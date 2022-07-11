package bold.alejo.weather.network.di

import bold.alejo.weather.core.data.DomainErrorFactory
import bold.alejo.weather.home.data.datasource.remote.WeatherRemoteDataSource
import bold.alejo.weather.home.data.repository.DefaultWeatherRepository
import bold.alejo.weather.network.Urls.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): WeatherRemoteDataSource = retrofit.create(WeatherRemoteDataSource::class.java)

    @Singleton
    @Provides
    fun providesRepository(apiService: WeatherRemoteDataSource, errorFactory: DomainErrorFactory) = DefaultWeatherRepository(apiService, errorFactory)
}
