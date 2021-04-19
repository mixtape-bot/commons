package mixtape.commons.extensions

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Creates a flow using this channel.
 */
fun <T> ReceiveChannel<T>.asFlow(): Flow<T> = flow {
  try {
    for (event in this@asFlow) {
      emit(event)
    }
  } catch (ex: CancellationException) {
    // ignore
  }
}
