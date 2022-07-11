package bold.alejo.weather.home.data.dto

import bold.alejo.weather.home.domain.model.Condition
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataDay(
    @Json(name = "avgtemp_c") val avgtemp_c: Double,
    @Json(name = "condition") val condition: Condition
)
