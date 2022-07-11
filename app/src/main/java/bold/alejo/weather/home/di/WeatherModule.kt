package bold.alejo.weather.home.di

import bold.alejo.weather.core.data.DomainErrorFactory
import bold.alejo.weather.home.data.datasource.remote.WeatherRemoteDataSource
import bold.alejo.weather.home.data.repository.DefaultWeatherRepository
import bold.alejo.weather.home.domain.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class WeatherModule {

    @Provides
    fun provideWeatherRepository(
        weatherRemoteDataSource: WeatherRemoteDataSource,
        domainErrorFactory: DomainErrorFactory
    ): WeatherRepository =
        DefaultWeatherRepository.getInstance(
            weatherRemoteDataSource,
            domainErrorFactory
        )

    @Provides
    fun provideDomainErrorFactory() =
        DomainErrorFactory()
}
