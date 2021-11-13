package mixtape.commons.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import mixtape.commons.jda.events.FlowingEventManager
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.GenericEvent
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Convenience method for creating an instance of [JDA]
 *
 * @param token
 *   The bot token to use.
 *
 * @param builder
 *   Configures the [JDA] instance.
 *
 * @return The created [JDA] instance.
 */
inline fun JDA(token: String, builder: JDABuilder.() -> Unit): JDA {
    contract {
        callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
    }

    return JDABuilder.createDefault(token)
        .apply(builder)
        .build()
}

/**
 * Convenience method that invokes [block] whenever [T] is emitted on [FlowingEventManager.events]
 *
 * @param scope
 *   Scope to launch the Job in.
 *
 * @param block
 *   Block to call whenever [T] is emitted.
 *
 * @return Job, can be used to cancel any further processing of [T].
 */
inline fun <reified T : GenericEvent> JDA.on(
    scope: CoroutineScope? = null,
    crossinline block: suspend T.() -> Unit
): Job {
    require(eventManager is FlowingEventManager) {
        "JDA#on can only be used with the ${FlowingEventManager::class.simpleName}."
    }

    return with(eventManager as FlowingEventManager) {
        on(scope ?: this, block)
    }
}
