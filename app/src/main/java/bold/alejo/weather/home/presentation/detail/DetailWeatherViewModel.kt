package bold.alejo.weather.home.presentation.detail

import androidx.lifecycle.viewModelScope
import bold.alejo.weather.core.presentation.BaseViewModel
import bold.alejo.weather.home.domain.model.DetailWeather
import bold.alejo.weather.home.domain.usecase.GetDetailWeatherUseCase
import bold.alejo.weather.utils.View.getDateFormatted
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailWeatherViewModel @Inject constructor(
    private val getDetailLocationWeather: GetDetailWeatherUseCase,
) : BaseViewModel<DatailWeatherViewState, DetailNavigation>() {

    var locationSelected: String? = null

    fun getDetailWeather(search: String) {
        setState(DatailWeatherViewState.Loading)
        viewModelScope.launch {
            try {
                locationSelected = search
                val detailWeather = getDetailLocationWeather(search)
                detailWeather?.let {
                    setBarChart(detailWeather)
                }
            } catch (throwable: Throwable) {
                setState(DatailWeatherViewState.Error)
            }
        }
    }

    fun retry() {
        locationSelected?.let {
            getDetailWeather(it)
        } ?: setState(DatailWeatherViewState.Error)
    }

    private fun setBarChart(detailWeather: DetailWeather) {
        val days = detailWeather.forecast.forecastdayItems
        val entries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()

        for (i in days.indices) {
            entries.add(BarEntry(days[i].day.avgTemp.toString().toFloat(), i))
            labels.add(days[i].date.getDateFormatted())
        }

        setState(
            DatailWeatherViewState.Content(
                detailWeather,
                entries,
                labels
            )
        )
    }
}
