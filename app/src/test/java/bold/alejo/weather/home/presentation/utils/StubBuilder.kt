package bold.alejo.weather.home.presentation.utils

import bold.alejo.weather.home.domain.model.Location

class StubBuilder {
    companion object {
        const val query = "Bogota"

        fun getFakeLocations(): List<Location> = listOf(
            Location("Bogota", "Colombia")
        )
    }
}
