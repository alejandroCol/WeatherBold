package bold.alejo.weather.home.presentation.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import bold.alejo.weather.R
import bold.alejo.weather.databinding.ActivityDetailWeatherBinding
import bold.alejo.weather.home.domain.model.DetailWeather
import bold.alejo.weather.home.presentation.search.HomeViewModel.Companion.CITY_EXTRA
import bold.alejo.weather.utils.View.hide
import bold.alejo.weather.utils.View.setSafeOnClickListener
import bold.alejo.weather.utils.View.show
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailWeatherActivity : AppCompatActivity() {

    private val viewModel: DetailWeatherViewModel by viewModels()
    private lateinit var binding: ActivityDetailWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeStates()
        getExtras()
        setupListeners()
    }

    private fun setupListeners() {
        binding.btRetry.setSafeOnClickListener { viewModel.retry() }
    }

    private fun getExtras() {
        intent.extras?.let {
            it.getString(CITY_EXTRA)?.let { city -> viewModel.getDetailWeather(city) }
        }
    }

    private fun setBarChart(entries: List<BarEntry>, labels: List<String>) {
        val barDataSet = BarDataSet(entries, getString(R.string.label_chart))
        val data = BarData(labels, barDataSet)
        binding.run {
            bcWeatherDays.setDescription(getString(R.string.chart_description))
            bcWeatherDays.data = data
            bcWeatherDays.animateY(5000)
        }
    }

    private fun observeStates() {
        viewModel.stateLiveData.observe(this) { handleViewState(it) }
    }

    private fun handleViewState(state: DatailWeatherViewState) {
        when (state) {
            is DatailWeatherViewState.Content -> showContent(state.detailWeather, state.listBarEntry, state.listLabel)
            is DatailWeatherViewState.Loading -> showLoading(true)
            is DatailWeatherViewState.Error -> showError()
        }
    }

    private fun showError() {
        showLoading(false)
        binding.run {
            svContent.hide()
            binding.btRetry.show()
            Snackbar.make(
                tvLocation,
                getString(R.string.error_connection),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun showLoading(show: Boolean) {
        binding.run {
            binding.btRetry.hide()
            if (show) {
                pbDetail.show()
                svContent.hide()
            } else {
                pbDetail.hide()
                svContent.show()
            }
        }
    }

    private fun showContent(
        detailWeather: DetailWeather,
        entries: List<BarEntry>,
        labels: List<String>
    ) {
        val currentTemp = detailWeather.current.temp.toString()
        setBarChart(entries, labels)
        binding.run {
            showLoading(false)
            tvLocation.text = detailWeather.location.name
            tvCondition.text = detailWeather.forecast.forecastdayItems.first().day.condition.text
            tvCurrentTemp.text = "$currentTemp°"
        }
    }
}
