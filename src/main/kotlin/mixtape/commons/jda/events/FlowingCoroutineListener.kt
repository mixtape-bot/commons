package mixtape.commons.jda.events

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.*
import mu.KLogger
import mu.KotlinLogging
import net.dv8tion.jda.api.events.GenericEvent

/**
 * A coroutine listener with its own scoped event flow.
 *
 * @param dispatcher The dispatcher to use.
 */
public open class FlowingCoroutineListener(dispatcher: CoroutineDispatcher = Dispatchers.Default) : CoroutineListener(dispatcher) {
    public companion object {
        @PublishedApi
        internal val logger: KLogger = KotlinLogging.logger { }
    }

    public open val eventFlow: MutableSharedFlow<GenericEvent> = MutableSharedFlow(extraBufferCapacity = Int.MAX_VALUE)

    public val events: Flow<GenericEvent>
        get() = eventFlow.buffer(UNLIMITED)

    /**
     * Convenience method that calls [block] whenever [T] is emitted on [eventFlow]
     *
     * @param scope The scope to launch the listener job in.
     * @param block The block to call.
     *
     * @return A [Job] that can be used to cancel further processing of [T]
     */
    public inline fun <reified T : GenericEvent> on(scope: CoroutineScope = this, noinline block: suspend T.() -> Unit): Job {
        return events
            .filterIsInstance<T>()
            .onEach { event ->
                event
                    .runCatching { block() }
                    .onFailure { logger.error(it) { "[${T::class.simpleName}]" } }
            }
            .launchIn(scope)
    }

    override suspend fun handleEvent(event: GenericEvent) {
        eventFlow.emit(event)
    }
}
