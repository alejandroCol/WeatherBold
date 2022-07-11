package bold.alejo.weather.home.domain.usecase

import bold.alejo.weather.home.domain.WeatherRepository
import bold.alejo.weather.home.domain.model.DetailWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDetailWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(search: String): DetailWeather? {
        return withContext(Dispatchers.IO) {
            weatherRepository.getDetailWeather(search)
        }
    }
}
