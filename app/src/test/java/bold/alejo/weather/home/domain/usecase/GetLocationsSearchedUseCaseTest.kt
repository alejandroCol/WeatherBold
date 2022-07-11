package bold.alejo.weather.home.domain.usecase

import bold.alejo.weather.home.domain.WeatherRepository
import bold.alejo.weather.home.presentation.utils.CoroutineTestExtension
import bold.alejo.weather.home.presentation.utils.StubBuilder.Companion.getFakeLocations
import bold.alejo.weather.home.presentation.utils.StubBuilder.Companion.query
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(CoroutineTestExtension::class)
class GetLocationsSearchedUseCaseTest {

    // Subject under test
    private lateinit var getLocationsSearched: GetLocationsSearchedUseCase

    @MockK
    private lateinit var weatherRepository: WeatherRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        getLocationsSearched = GetLocationsSearchedUseCase(
            weatherRepository
        )
    }

    @Test
    fun `Given query search, when the app tries to find locations using that query, Then return the location list`() =
        runBlocking {
            //  Given
            coEvery { weatherRepository.getSearchedLocations(query) } returns getFakeLocations()

            // When
            val result = getLocationsSearched(query)

            // Then
            coVerify(exactly = 1) { weatherRepository.getSearchedLocations(query) }

            Assertions.assertEquals(getFakeLocations(), result)
        }
}
