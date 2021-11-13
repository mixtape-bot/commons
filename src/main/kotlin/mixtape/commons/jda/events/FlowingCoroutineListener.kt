package mixtape.commons.jda.events

import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import mu.KotlinLogging
import net.dv8tion.jda.api.events.GenericEvent

open class FlowingCoroutineListener : CoroutineListener() {
    companion object {
        @PublishedApi
        internal val logger = KotlinLogging.logger { }
    }

    val flow = MutableSharedFlow<GenericEvent>(extraBufferCapacity = Int.MAX_VALUE)

    inline fun <reified T : GenericEvent> on(noinline block: suspend T.() -> Unit): Job {
        return flow
            .buffer(Channel.UNLIMITED)
            .filterIsInstance<T>()
            .onEach { event ->
                event
                    .runCatching { block() }
                    .onFailure { logger.error(it) { "[${T::class.simpleName}]" } }
            }
            .launchIn(this)
    }

    final override suspend fun handleEvent(event: GenericEvent) {
        flow.emit(event)
    }
}
