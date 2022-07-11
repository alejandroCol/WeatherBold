package bold.alejo.weather.home.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataDetailWeather(
    @Json(name = "location") val location: DataLocation,
    @Json(name = "forecast") val forecast: DataForecastDay,
    @Json(name = "current") val current: DataCurrentWeather
)
