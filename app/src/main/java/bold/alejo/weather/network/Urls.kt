package bold.alejo.weather.network

object Urls {
    const val BASE_URL = "https://api.weatherapi.com/"
    const val API_KEY = "de5553176da64306b86153651221606"
    const val DAYS = 3

    private const val API_VERSION_1 = "v1/"

    const val SEARCH = API_VERSION_1 + "search.json"
    const val FORECAST = API_VERSION_1 + "forecast.json"

}
