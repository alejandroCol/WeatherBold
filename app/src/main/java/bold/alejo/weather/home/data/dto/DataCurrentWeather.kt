package bold.alejo.weather.home.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataCurrentWeather(
    @Json(name = "temp_c") val temp_c: Double
)
