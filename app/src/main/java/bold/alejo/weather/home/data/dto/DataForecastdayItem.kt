package bold.alejo.weather.home.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataForecastdayItem(
    @Json(name = "date") val date: String,
    @Json(name = "day") val day: DataDay

)
