package bold.alejo.weather.home.presentation.search

import android.accounts.NetworkErrorException
import androidx.lifecycle.Observer
import bold.alejo.weather.core.presentation.OneTimeEvent
import bold.alejo.weather.home.domain.usecase.GetLocationsSearchedUseCase
import bold.alejo.weather.home.presentation.utils.CoroutineTestExtension
import bold.alejo.weather.home.presentation.utils.InstantExecutorExtension
import bold.alejo.weather.home.presentation.utils.StubBuilder.Companion.getFakeLocations
import bold.alejo.weather.home.presentation.utils.StubBuilder.Companion.query
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class HomeViewModelTest {

    // Subject under test
    private lateinit var homeViewModel: HomeViewModel

    @MockK
    private lateinit var getLocationsSearched: GetLocationsSearchedUseCase

    @RelaxedMockK
    private lateinit var navigationObserver: Observer<OneTimeEvent<HomeNavigation>>

    @RelaxedMockK
    private lateinit var homeStateObserver: Observer<HomeViewState>

    @JvmField
    @RegisterExtension
    val coroutineExtension = CoroutineTestExtension()

    private val homeCapturedState = slot<HomeViewState>()
    private val homeStateList = arrayListOf<HomeViewState>()
    private val navigationCaptured = slot<OneTimeEvent<HomeNavigation>>()
    private val navigationList = arrayListOf<HomeNavigation>()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        homeViewModel = HomeViewModel(
            getLocationsSearched
        )

        homeViewModel.navigationLiveData.observeForever(navigationObserver)
        homeViewModel.stateLiveData.observeForever(homeStateObserver)

        coEvery { homeStateObserver.onChanged(capture(homeCapturedState)) } answers {
            homeStateList.add(homeCapturedState.captured)
        }

        coEvery { navigationObserver.onChanged(capture(navigationCaptured)) } answers {
            navigationList.add(navigationCaptured.captured.peekContent()!!)
        }
    }

    @AfterEach
    fun tearDown() {
        homeViewModel.navigationLiveData.removeObserver(navigationObserver)
        homeViewModel.stateLiveData.removeObserver(homeStateObserver)

        homeStateList.clear()
        navigationList.clear()
    }

    @Nested
    inner class ViewInitialized {
        @Test
        fun `Given query to search, When get locations weather, Then show Content state`() =
            runBlocking {
                // Given
                coEvery { getLocationsSearched(query) } returns getFakeLocations()

                // When
                homeViewModel.searchLocations(query)

                // Then
                Assertions.assertEquals(2, homeStateList.size)
                Assertions.assertEquals(HomeViewState.Loading, homeStateList.first())
                Assertions.assertEquals(HomeViewState.Content(getFakeLocations()), homeStateList.last())
            }
    }

    @Test
    fun `Given query to search, When get empty list weather, Then show Empty state`() =
        runBlocking {
            // Given
            coEvery { getLocationsSearched(query) } returns emptyList()

            // When
            homeViewModel.searchLocations(query)

            // Then
            Assertions.assertEquals(2, homeStateList.size)
            Assertions.assertEquals(HomeViewState.Loading, homeStateList.first())
            Assertions.assertEquals(HomeViewState.Empty, homeStateList.last())
        }

    @Test
    fun `Given query to search, When get exception service, Then show Error state`() =
        runBlocking {
            // Given
            coEvery { getLocationsSearched(query) } throws NetworkErrorException()

            // When
            homeViewModel.searchLocations(query)

            // Then
            Assertions.assertEquals(2, homeStateList.size)
            Assertions.assertEquals(HomeViewState.Loading, homeStateList.first())
            Assertions.assertEquals(HomeViewState.Error, homeStateList.last())
        }
}
