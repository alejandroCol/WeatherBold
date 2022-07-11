package bold.alejo.weather.home.presentation.search

import bold.alejo.weather.home.domain.model.Location

sealed interface HomeViewState {
    object Error : HomeViewState
    object Loading : HomeViewState
    object Empty : HomeViewState
    data class Content(
        val locations: List<Location>,
    ) : HomeViewState
}

sealed interface HomeNavigation {
    data class Detail(val cityName: String) : HomeNavigation
}
