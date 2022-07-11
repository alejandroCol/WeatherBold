package bold.alejo.weather.home.data.datasource.remote

import bold.alejo.weather.home.data.dto.DataDetailWeather
import bold.alejo.weather.home.data.dto.DataLocation
import bold.alejo.weather.network.Urls
import bold.alejo.weather.utils.MoshiEndpoint
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRemoteDataSource {
    @MoshiEndpoint
    @GET(Urls.SEARCH)
    suspend fun fetchSearchLocations(@Query("q") search: String, @Query("key") key: String): List<DataLocation>

    @MoshiEndpoint
    @GET(Urls.FORECAST)
    suspend fun fetchForecast(@Query("key") key: String, @Query("q") search: String, @Query("days") type: Int): DataDetailWeather
}
