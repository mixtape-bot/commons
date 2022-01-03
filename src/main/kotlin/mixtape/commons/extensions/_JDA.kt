package mixtape.commons.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import mixtape.commons.jda.events.FlowingEventManager
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public val JDABuilder.intents: IntentProvider
    get() = IntentProvider(this)

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
public inline fun JDA(token: String, builder: JDABuilder.() -> Unit): JDA {
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
public inline fun <reified T : GenericEvent> JDA.on(
    scope: CoroutineScope? = null,
    noinline block: suspend T.() -> Unit,
): Job {
    require(eventManager is FlowingEventManager) {
        "JDA#on can only be used with the ${FlowingEventManager::class.simpleName}."
    }

    return with(eventManager as FlowingEventManager) {
        on(scope ?: this, block)
    }
}

public class IntentProvider(private val builder: JDABuilder) {
    public operator fun plusAssign(intents: Collection<GatewayIntent>) {
        builder.enableIntents(intents)
    }

    public operator fun plusAssign(intent: GatewayIntent) {
        builder.enableIntents(intent)
    }

    public operator fun minusAssign(intents: Collection<GatewayIntent>) {
        builder.disableIntents(intents)
        CacheFlag.values()
            .filter { it.requiredIntent in intents }
            .forEach { builder.disableCache(it) }
    }

    public operator fun minusAssign(intent: GatewayIntent) {
        builder.disableIntents(intent)
        CacheFlag.values()
            .filter { it.requiredIntent == intent }
            .forEach { builder.disableCache(it) }
    }
}
