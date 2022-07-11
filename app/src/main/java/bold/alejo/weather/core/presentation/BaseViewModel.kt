package bold.alejo.weather.core.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseViewModel<STATE, NAVIGATION> : ViewModel() {

    protected val state: MutableLiveData<STATE> = MutableLiveData()
    val stateLiveData = state.asLiveData()

    protected val navigation: MutableLiveData<OneTimeEvent<NAVIGATION>> = MutableLiveData()
    val navigationLiveData = navigation.asLiveData()

    fun hasViewState() = state.value != null

    fun setState(newState: STATE) {
        state.postValue(newState)
    }

    fun setValue(newState: STATE) {
        state.value = newState
    }

    fun navigateTo(newNavigation: NAVIGATION) {
        navigation.postValue(OneTimeEvent(newNavigation))
    }
}
