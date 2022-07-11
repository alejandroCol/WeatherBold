package bold.alejo.weather.home.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataLocation(
    @Json(name = "name") val name: String,
    @Json(name = "country") val country: String
)
