package mixtape.commons.jda.events

import kotlinx.coroutines.*
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.hooks.EventListener
import kotlin.coroutines.CoroutineContext

public abstract class CoroutineListener(private val dispatcher: CoroutineDispatcher = Dispatchers.Default) : EventListener, CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = dispatcher + SupervisorJob()

    final override fun onEvent(event: GenericEvent) {
        launch {
            handleEvent(event)
        }
    }

    public abstract suspend fun handleEvent(event: GenericEvent)
}
