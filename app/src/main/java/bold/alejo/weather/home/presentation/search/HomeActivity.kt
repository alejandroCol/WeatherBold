package bold.alejo.weather.home.presentation.search

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import bold.alejo.weather.R
import bold.alejo.weather.core.presentation.OneTimeEventObserver
import bold.alejo.weather.databinding.ActivityHomeBinding
import bold.alejo.weather.home.domain.model.Location
import bold.alejo.weather.home.presentation.adapter.LocationsAdapter
import bold.alejo.weather.home.presentation.detail.DetailWeatherActivity
import bold.alejo.weather.home.presentation.search.HomeViewModel.Companion.CITY_EXTRA
import bold.alejo.weather.utils.DebouncingQueryTextListener
import bold.alejo.weather.utils.View.hide
import bold.alejo.weather.utils.View.show
import bold.alejo.weather.utils.View.showKeyboard
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        observeStates()
        observeNavigation()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupList()
        setupSearchView()
    }

    private fun setupList() {
        binding.locationsRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            setHasFixedSize(true)
            adapter = LocationsAdapter(viewModel)
            addItemDecoration(DividerItemDecoration(context, 0))
        }
    }

    private fun setupSearchView() {
        binding.run {
            searchView.setOnQueryTextListener(
                DebouncingQueryTextListener(
                    this@HomeActivity.lifecycle
                ) { newText ->
                    newText?.let {
                        if (it.isEmpty()) {
                            viewModel.cleanSearch()
                        } else {
                            viewModel.searchLocations(it)
                        }
                    }
                }
            )
            searchView.setIconifiedByDefault(false)
            searchView.requestFocus()
            searchView.showKeyboard()
        }
    }

    private fun observeNavigation() {
        viewModel.navigationLiveData.observe(
            this,
            OneTimeEventObserver {
                handleNavigation(it)
            }
        )
    }

    private fun observeStates() {
        viewModel.stateLiveData.observe(this) { handleViewState(it) }
    }

    private fun handleNavigation(navigation: HomeNavigation) {
        when (navigation) {
            is HomeNavigation.Detail -> openDetail(navigation.cityName)
        }
    }

    private fun openDetail(city: String) {
        val intent = Intent(this, DetailWeatherActivity::class.java)
        intent.putExtra(CITY_EXTRA, city)
        startActivity(intent)
    }

    private fun handleViewState(state: HomeViewState) {
        when (state) {
            is HomeViewState.Content -> showContent(state.locations)
            is HomeViewState.Empty -> showEmpty()
            is HomeViewState.Loading -> showLoading(true)
            is HomeViewState.Error -> showError()
        }
    }

    private fun showError() {
        showLoading(false)
        Snackbar.make(
            binding.searchView,
            getString(R.string.error_connection),
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun showEmpty() {
        showLoading(false)
        binding.locationsRecyclerview.hide()
        binding.layoutEmptySearch.show()
    }

    private fun showContent(locationsList: List<Location>) {
        showLoading(false)
        binding.run {
            layoutEmptySearch.hide()
            locationsRecyclerview.show()
            locationsRecyclerview.apply {
                with(adapter as LocationsAdapter) {
                    locations = locationsList
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.run {
            if (show) {
                pbHome.show()
                layoutEmptySearch
            } else pbHome.hide()
        }
    }
}
