package mixtape.commons

import java.util.*
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

fun threadFactory(
  name: String,
  daemon: Boolean? = null,
  priority: Int? = null,
  exceptionHandler: Thread.UncaughtExceptionHandler? = null
): ThreadFactory {
  val counter = AtomicInteger()
  return ThreadFactory { runnable ->
    Thread(runnable, name.format(Locale.ROOT, counter.getAndIncrement())).apply {
      daemon?.let {
        this.isDaemon = it
      }

      priority?.let {
        this.priority = priority
      }

      exceptionHandler?.let {
        this.uncaughtExceptionHandler = it
      }
    }
  }
}
