package bold.alejo.weather.home.presentation.listener

import bold.alejo.weather.home.domain.model.Location

interface LocationsListener {
    fun onLocationClicked(location: Location)
}
