package mixtape.commons

import java.util.*
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

public fun threadFactory(
    name: String = "",
    daemon: Boolean? = null,
    priority: Int? = null,
    exceptionHandler: Thread.UncaughtExceptionHandler? = null
): ThreadFactory = Threads.createFactory(name, daemon, priority, exceptionHandler)

public object Threads {
    private val poolCounter = AtomicInteger()

    public fun createFactory(name: String = "", daemon: Boolean? = null, priority: Int? = null, exceptionHandler: Thread.UncaughtExceptionHandler? = null): Factory {
        return object : Factory {
            override val poolId = poolCounter.getAndIncrement()

            val counter = AtomicInteger()
            val nameFormat = name.ifBlank { "mix-thread-pool-$poolId-thread-%d" }

            override fun newThread(r: Runnable): Thread {
                return Thread(r, nameFormat.format(Locale.ROOT, counter.getAndIncrement())).apply {
                    daemon?.let { isDaemon = it }
                    priority?.let { this.priority = it }
                    exceptionHandler?.let { uncaughtExceptionHandler = it }
                }
            }
        }
    }

    public interface Factory: ThreadFactory {
        public val poolId: Int
    }
}
