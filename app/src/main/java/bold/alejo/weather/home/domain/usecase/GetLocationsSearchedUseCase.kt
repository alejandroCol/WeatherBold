package bold.alejo.weather.home.domain.usecase

import bold.alejo.weather.home.domain.WeatherRepository
import bold.alejo.weather.home.domain.model.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLocationsSearchedUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(search: String): List<Location>? {
        return withContext(Dispatchers.IO) {
            weatherRepository.getSearchedLocations(search)
        }
    }
}
