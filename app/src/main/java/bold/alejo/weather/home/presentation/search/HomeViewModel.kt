package bold.alejo.weather.home.presentation.search

import androidx.lifecycle.viewModelScope
import bold.alejo.weather.core.presentation.BaseViewModel
import bold.alejo.weather.home.domain.model.Location
import bold.alejo.weather.home.domain.usecase.GetLocationsSearchedUseCase
import bold.alejo.weather.home.presentation.listener.LocationsListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLocationsSearched: GetLocationsSearchedUseCase,
) : BaseViewModel<HomeViewState, HomeNavigation>(), LocationsListener {

    var emptyList = true

    fun searchLocations(search: String) {
        if (emptyList) setState(HomeViewState.Loading)
        viewModelScope.launch {
            try {
                val locations = getLocationsSearched(search)
                locations?.let {
                    if (it.isNotEmpty()) {
                        emptyList = false
                        setState(HomeViewState.Content(it))
                    } else setEmpty()
                } ?: setEmpty()
            } catch (throwable: Throwable) {
                setState(HomeViewState.Error)
            }
        }
    }

    private fun setEmpty() {
        emptyList = true
        setState(HomeViewState.Empty)
    }

    fun cleanSearch() {
        setState(HomeViewState.Empty)
    }

    override fun onLocationClicked(location: Location) {
        navigateTo(HomeNavigation.Detail(location.name))
    }

    companion object {
        const val CITY_EXTRA = "cali"
    }
}
