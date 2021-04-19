package gg.mixtape.commons.extensions

import gg.mixtape.commons.jda.FlowingEventManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.GenericEvent

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
inline fun <reified T : GenericEvent> JDA.on(scope: CoroutineScope? = null, crossinline block: T.() -> Unit): Job {
  require(eventManager is FlowingEventManager) {
    "JDA#on can only be used with the ${FlowingEventManager::class.simpleName}."
  }

  return with(eventManager as FlowingEventManager) {
    on(scope ?: this, block)
  }
}
