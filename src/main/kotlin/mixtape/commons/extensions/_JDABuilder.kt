package mixtape.commons.extensions

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import mixtape.commons.jda.events.FlowingEventManager
import net.dv8tion.jda.api.JDABuilder

/**
 * Make use of the [FlowingEventManager], allows the use of [on]
 *
 * @param dispatcher
 *   The [CoroutineDispatcher] to use in [FlowingEventManager.on]
 *
 * @return JDABuilder, useful for chaining
 */
public fun JDABuilder.useFlowingEventManager(dispatcher: CoroutineDispatcher = Dispatchers.Default): JDABuilder {
    setEventManager(FlowingEventManager(dispatcher))
    return this
}
