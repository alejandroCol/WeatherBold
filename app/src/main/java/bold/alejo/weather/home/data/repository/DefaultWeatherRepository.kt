package bold.alejo.weather.home.data.repository

import bold.alejo.weather.core.data.DomainErrorFactory
import bold.alejo.weather.core.data.runWith
import bold.alejo.weather.home.data.datasource.remote.WeatherRemoteDataSource
import bold.alejo.weather.home.data.mapper.toDomainEntity
import bold.alejo.weather.home.domain.WeatherRepository
import bold.alejo.weather.home.domain.model.DetailWeather
import bold.alejo.weather.home.domain.model.Location
import bold.alejo.weather.network.Urls.API_KEY
import bold.alejo.weather.network.Urls.DAYS
import bold.alejo.weather.utils.Singleton

class DefaultWeatherRepository(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val errorFactory: DomainErrorFactory,
) : WeatherRepository {

    override suspend fun getSearchedLocations(search: String): List<Location> {
        return runWith(errorFactory) {
            remoteDataSource.fetchSearchLocations(search, API_KEY)
                .map { it.toDomainEntity() }.toMutableList()
        }
    }

    override suspend fun getDetailWeather(search: String): DetailWeather {
        return runWith(errorFactory) {
            val remote = remoteDataSource.fetchForecast(API_KEY, search, DAYS)
            remote.toDomainEntity()
        }
    }

    companion object : Singleton() {
        @JvmStatic
        fun getInstance(
            remoteDataSource: WeatherRemoteDataSource,
            errorFactory: DomainErrorFactory,
        ) = get {
            DefaultWeatherRepository(remoteDataSource, errorFactory)
        }
    }
}
