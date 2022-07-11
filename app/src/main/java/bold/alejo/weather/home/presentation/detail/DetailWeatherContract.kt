package bold.alejo.weather.home.presentation.detail

import bold.alejo.weather.home.domain.model.DetailWeather
import com.github.mikephil.charting.data.BarEntry

sealed interface DatailWeatherViewState {
    object Loading : DatailWeatherViewState
    object Error : DatailWeatherViewState
    data class Content(
        val detailWeather: DetailWeather,
        val listBarEntry: List<BarEntry>,
        val listLabel: List<String>
    ) : DatailWeatherViewState
}

sealed interface DetailNavigation
