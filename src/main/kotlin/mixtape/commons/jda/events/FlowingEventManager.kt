package mixtape.commons.jda.events

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import mu.KotlinLogging
import net.dv8tion.jda.api.events.ExceptionEvent
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.ShutdownEvent
import net.dv8tion.jda.api.hooks.EventListener
import net.dv8tion.jda.api.hooks.IEventManager
import kotlin.coroutines.CoroutineContext

/**
 * An [IEventManager] implementation that makes use of [Job]s, [Channel]s, and [Flow]s.
 * The [FlowingEventManager] only allows use of [EventListener].
 *
 * @param dispatcher
 *   The dispatcher to use, it's recommended to use the
 *   [net.dv8tion.jda.internal.utils.config.ThreadingConfig.eventPool] as a coroutine dispatcher
 */
class FlowingEventManager(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val eventFlow: MutableSharedFlow<GenericEvent> = MutableSharedFlow(extraBufferCapacity = Int.MAX_VALUE)
) : IEventManager, CoroutineScope {
    companion object {
        val logger = KotlinLogging.logger { }
    }

    override val coroutineContext: CoroutineContext
        get() = dispatcher + SupervisorJob()

    val events
        get() = eventFlow.buffer(Channel.UNLIMITED)

    private val listeners = hashMapOf<EventListener, Job>()

    /**
     * Launches a processing job of [T]
     *
     * @param scope
     *
     * @param block
     *   Block that will be invoked whenever [T] is emitted on [events]
     *
     * @return Job
     */
    inline fun <reified T : GenericEvent> on(
        scope: CoroutineScope = this,
        noinline block: suspend T.() -> Unit
    ): Job {
        return events
            .filterIsInstance<T>()
            .onEach { event ->
                event
                    .runCatching { block() }
                    .onFailure { logger.error(it) { "Error occurred while handling ${T::class.simpleName}" } }
            }
            .launchIn(scope)
    }

    /**
     *
     */
    override fun handle(event: GenericEvent) = runBlocking {
        try {
            eventFlow.emit(event)
        } catch (ex: Exception) {
            eventFlow.emit(ExceptionEvent(event.jda, ex, false))
        }

        if (event is ShutdownEvent) {
            currentCoroutineContext().cancel()
        }
    }

    /**
     * Registers the provided [listener]
     *
     * @param listener
     *   The [EventListener] to register
     */
    override fun register(listener: Any) {
        require(listener is EventListener) { "Listener must implement EventListener" }
        listeners[listener] = on(this, listener::onEvent)
    }

    /**
     * Unregisters the provided [listener]
     *
     * @param listener
     *   The [EventListener] to remove
     */
    override fun unregister(listener: Any) {
        require(listener is EventListener) { "Listener must implement EventListener" }
        listeners.remove(listener)?.cancel()
    }

    /**
     * Returns all registered listeners in [listeners]
     */
    override fun getRegisteredListeners(): List<Any> {
        return listeners.keys.toList()
    }
}
