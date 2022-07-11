package bold.alejo.weather.home.domain.usecase

import bold.alejo.weather.home.domain.WeatherRepository
import bold.alejo.weather.home.domain.model.DetailWeather
import bold.alejo.weather.home.presentation.utils.CoroutineTestExtension
import bold.alejo.weather.home.presentation.utils.StubBuilder.Companion.query
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(CoroutineTestExtension::class)
class GetDetailWeatherUseCaseTest {

    // Subject under test
    private lateinit var getDetailWeather: GetDetailWeatherUseCase

    @MockK
    private lateinit var weatherRepository: WeatherRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        getDetailWeather = GetDetailWeatherUseCase(
            weatherRepository
        )
    }

    @Test
    fun `Given location name, when the app tries to find detail weather using that name, Then return weather detail`() =
        runBlocking {
            //  Given
            val detailWeather = mockk<DetailWeather>()

            coEvery { weatherRepository.getDetailWeather(query) } returns detailWeather

            // When
            val result = getDetailWeather(query)

            // Then
            coVerify(exactly = 1) { weatherRepository.getDetailWeather(query) }

            Assertions.assertEquals(detailWeather, result)
        }
}
