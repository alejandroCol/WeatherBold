package bold.alejo.weather.utils

abstract class Singleton {
    private var instance: Any? = null

    @Suppress("UNCHECKED_CAST")
    protected fun <TYPE> get(createInstance: () -> TYPE): TYPE {
        synchronized(this) {
            return (instance ?: createInstance().also { instance = it }) as TYPE
        }
    }

    fun destroyInstance() {
        instance = null
    }
}
