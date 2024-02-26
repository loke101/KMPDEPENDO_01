package com.jetbrains.dependoapp.presentation.screens.location

import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



class GeoViewModel(
    public val locationTracker: LocationTracker
) : ViewModel() {
    private var _result: MutableStateFlow<String?> = MutableStateFlow("press button")
    val result: StateFlow<String?> get() = _result

    init {
        locationTracker.getLocationsFlow()
            .distinctUntilChanged()
            .onEach { _result.value = it.toString() }
            .launchIn(viewModelScope)
    }
    fun resetLocation(){
        _result.value = ""
    }

    fun onStartClick() {
        viewModelScope.launch {
            try {
                locationTracker.startTracking()
            } catch (exc: Exception) {
                _result.value = exc.toString()
            }
        }
    }

    fun onStopClick() {
        locationTracker.stopTracking()
    }
}