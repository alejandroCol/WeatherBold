package bold.alejo.weather.home.data.mapper

import bold.alejo.weather.home.data.dto.*
import bold.alejo.weather.home.domain.model.*

fun DataLocation.toDomainEntity(): Location = Location(
    name,
    country
)

fun Location.toDto(): DataLocation = DataLocation(
    name,
    country
)

fun DataDetailWeather.toDomainEntity(): DetailWeather = DetailWeather(
    forecast.toDomainEntity(),
    location.toDomainEntity(),
    current.toDomainEntity()
)

fun DetailWeather.toDto(): DataDetailWeather = DataDetailWeather(
    location.toDto(),
    forecast.toDto(),
    current.toDto()
)

fun DataForecastDay.toDomainEntity(): ForecastDay = ForecastDay(
    forecastday.map { it.toDomainEntity() }.toMutableList()
)

fun ForecastDay.toDto(): DataForecastDay = DataForecastDay(
    forecastdayItems.map { it.toDto() }.toMutableList()
)

fun DataDay.toDomainEntity(): Day = Day(
    avgtemp_c,
    condition
)

fun Day.toDto(): DataDay = DataDay(
    avgTemp,
    condition
)

fun DataForecastdayItem.toDomainEntity(): ForecastdayItem = ForecastdayItem(
    day.toDomainEntity(),
    date
)

fun ForecastdayItem.toDto(): DataForecastdayItem = DataForecastdayItem(
    date,
    day.toDto(),
)

fun DataCondition.toDomainEntity(): Condition = Condition(
    text
)

fun Condition.toDto(): DataCondition = DataCondition(
    text
)

fun DataCurrentWeather.toDomainEntity(): CurrentWeather = CurrentWeather(
    temp_c
)

fun CurrentWeather.toDto(): DataCurrentWeather = DataCurrentWeather(
    temp
)


