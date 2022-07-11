package bold.alejo.weather.home.presentation.detail

import androidx.lifecycle.Observer
import bold.alejo.weather.core.presentation.OneTimeEvent
import bold.alejo.weather.home.domain.model.DetailWeather
import bold.alejo.weather.home.domain.usecase.GetDetailWeatherUseCase
import bold.alejo.weather.home.presentation.utils.CoroutineTestExtension
import bold.alejo.weather.home.presentation.utils.InstantExecutorExtension
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class DetailWeatherViewModelTest {

    // Subject under test
    private lateinit var detailWeatherViewModel: DetailWeatherViewModel

    @MockK
    private lateinit var getDetailWeather: GetDetailWeatherUseCase

    @RelaxedMockK
    private lateinit var navigationObserver: Observer<OneTimeEvent<DetailNavigation>>

    @RelaxedMockK
    private lateinit var detailStateObserver: Observer<DatailWeatherViewState>

    private val detailCapturedState = slot<DatailWeatherViewState>()
    private val detailStateList = arrayListOf<DatailWeatherViewState>()

    private val navigationCaptured = slot<OneTimeEvent<DetailNavigation>>()
    private val navigationList = arrayListOf<DetailNavigation>()

    @JvmField
    @RegisterExtension
    val coroutineExtension = CoroutineTestExtension()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(Dispatchers.IO)

        MockKAnnotations.init(this, relaxUnitFun = true)

        detailWeatherViewModel = DetailWeatherViewModel(
            getDetailWeather
        )

        detailWeatherViewModel.navigationLiveData.observeForever(navigationObserver)
        detailWeatherViewModel.stateLiveData.observeForever(detailStateObserver)

        coEvery { detailStateObserver.onChanged(capture(detailCapturedState)) } answers {
            detailStateList.add(detailCapturedState.captured)
        }

        coEvery { navigationObserver.onChanged(capture(navigationCaptured)) } answers {
            navigationList.add(navigationCaptured.captured.peekContent()!!)
        }
    }

    @AfterEach
    fun tearDown() {
        detailWeatherViewModel.navigationLiveData.removeObserver(navigationObserver)
        detailWeatherViewModel.stateLiveData.removeObserver(detailStateObserver)

        detailStateList.clear()
        navigationList.clear()
        Dispatchers.resetMain()
    }

    @Test
    fun `Given a location, When get detail weather, Then show Loading state`() =
        runBlocking {
            // Given
            val query = "bogota"
            val detailWeather = mockk<DetailWeather>(relaxed = true)

            coEvery { getDetailWeather(query) } returns detailWeather

            // When
            detailWeatherViewModel.getDetailWeather(query)

            // Then
            Assertions.assertEquals(DatailWeatherViewState.Loading, detailStateList.first())
            Assertions.assertEquals(1, detailStateList.size)
        }
}
