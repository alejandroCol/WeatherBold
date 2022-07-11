package bold.alejo.weather.home.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataForecastDay(
    @Json(name = "forecastday") val forecastday: List<DataForecastdayItem>
)
