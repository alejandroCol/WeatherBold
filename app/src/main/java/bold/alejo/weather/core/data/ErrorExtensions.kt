package bold.alejo.weather.core.data

import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.isConnectionError(): Boolean {
    return cause is SocketTimeoutException ||
        cause is IOException ||
        cause is ConnectException ||
        cause is UnknownHostException
}

inline fun <T> runWith(errorFactory: DomainErrorFactory, block: () -> T): T {
    return try {
        block.invoke()
    } catch (throwable: Throwable) {
        throw errorFactory.build(throwable)
    }
}
