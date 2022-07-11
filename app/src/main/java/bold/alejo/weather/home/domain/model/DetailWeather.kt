package bold.alejo.weather.home.domain.model

data class DetailWeather(
    val forecast: ForecastDay,
    val location: Location,
    val current: CurrentWeather
)
