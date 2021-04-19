package gg.mixtape.commons.jda

import gg.mixtape.commons.extensions.asFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import net.dv8tion.jda.api.events.ExceptionEvent
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.ShutdownEvent
import net.dv8tion.jda.api.hooks.EventListener
import net.dv8tion.jda.api.hooks.IEventManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
  private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : IEventManager, CoroutineScope {
  override val coroutineContext: CoroutineContext
    get() = dispatcher + SupervisorJob()

  val events
    get() = channel.openSubscription().asFlow().buffer(Channel.UNLIMITED)

  private val listeners = hashMapOf<EventListener, Job>()
  private val channel = BroadcastChannel<GenericEvent>(1)

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
  inline fun <reified T : GenericEvent> on(scope: CoroutineScope = this, crossinline block: T.() -> Unit): Job {
    return events.filterIsInstance<T>().onEach { event ->
      event
        .runCatching { block() }
        .onFailure { logger.error("Error occurred while handling ${T::class.simpleName}", it) }
    }.launchIn(scope)
  }

  /**
   *
   */
  override fun handle(event: GenericEvent) = runBlocking {
    try {
      channel.send(event)
    } catch (ex: Exception) {
      channel.send(ExceptionEvent(event.jda, ex, false))
    }

    if (event is ShutdownEvent) {
      channel.cancel()
    }
  }

  /**
   * Registers the provided [listener]
   *
   * @param listener
   *   The [EventListener] to register
   */
  override fun register(listener: Any) {
    require(listener is EventListener) {
      "Listener must implement EventListener"
    }

    listeners[listener] = on(this, listener::onEvent)
  }

  /**
   * Unregisters the provided [listener]
   *
   * @param listener
   *   The [EventListener] to remove
   */
  override fun unregister(listener: Any) {
    require(listener is EventListener) {
      "Listener must implement EventListener"
    }

    listeners.remove(listener)?.cancel()
  }

  /**
   * Returns all registered listeners in [listeners]
   */
  override fun getRegisteredListeners(): List<Any> {
    return listeners.keys.toList()
  }

  companion object {
    val logger: Logger = LoggerFactory.getLogger(FlowingEventManager::class.java)
  }
}
