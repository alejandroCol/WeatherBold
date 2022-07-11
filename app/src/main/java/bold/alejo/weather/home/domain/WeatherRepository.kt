package bold.alejo.weather.home.domain

import bold.alejo.weather.home.domain.model.DetailWeather
import bold.alejo.weather.home.domain.model.Location

interface WeatherRepository {
    suspend fun getSearchedLocations(search: String): List<Location>
    suspend fun getDetailWeather(search: String): DetailWeather
}
