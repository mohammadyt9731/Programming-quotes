package com.example.programmingquotes.feature.authors.ui

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.authors.data.repository.AuthorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AuthorViewModel @Inject constructor(
    private val repository: AuthorRepository,
    private val sensorManager: SensorManager
) : ViewModel() {

    private val _viewState = MutableStateFlow(AuthorViewState())
    val viewState = _viewState.asStateFlow()

    val errorChannel = Channel<String>()

    private var isNextRequestReady = true

    private var sensorListener: SensorEventListener? = null

    init {
        getAuthors()
    }

    fun handleAction(action: AuthorAction) {
        when (action) {
            AuthorAction.GetRandomQuote -> {
                getRandomQuote()
            }
            AuthorAction.RefreshAuthors -> {
                getAuthors(isRefresh = true)
            }
        }
    }

    private fun getAuthors(isRefresh: Boolean = false) = viewModelScope.launch(Dispatchers.IO) {
        _viewState.emit(_viewState.value.copy(pageState = ResultWrapper.Loading))
        repository.getAuthors(isRefresh).collect {
            if (it is ResultWrapper.Error) {
                errorChannel.send(it.errors.message)
                _viewState.emit(_viewState.value.copy(pageState = ResultWrapper.Error(it.errors)))
            } else if (it is ResultWrapper.Success) {
                _viewState.emit(_viewState.value.copy(pageState = ResultWrapper.Success(data = it.data)))
            }
        }
    }

    private fun getRandomQuote() = viewModelScope.launch(Dispatchers.IO) {
        _viewState.emit(_viewState.value.copy(bottomSheetState = ResultWrapper.Loading))
        repository.getRandomQuote()
            .collect {
                if (it is ResultWrapper.Error) {
                    errorChannel.send(it.errors.message)
                } else if (it is ResultWrapper.Success) {
                    it.data?.let { quoteView ->
                        _viewState.emit(
                            _viewState.value.copy(
                                bottomSheetState = ResultWrapper.Success(
                                    data = quoteView
                                )
                            )
                        )
                    }
                }
            }
        isNextRequestReady = true
    }


    //sensor
    fun startSensorManager() = setUpSensorManager()

    suspend fun stopSensorManager() {
        sensorManager.unregisterListener(sensorListener)
        _viewState.emit(_viewState.value.copy(bottomSheetState = ResultWrapper.UnInitialize))
    }

    private fun setUpSensorManager() {

        var acceleration = 0f
        var currentAcceleration = 0f
        var lastAcceleration: Float

        sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                lastAcceleration = currentAcceleration

                currentAcceleration = StrictMath.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                val delta: Float = currentAcceleration - lastAcceleration
                acceleration = acceleration * 0.9f + delta

                viewModelScope.launch {
                    if (acceleration > 12) {
                        if (isNextRequestReady) {
                            isNextRequestReady = false
                            handleAction(AuthorAction.GetRandomQuote)
                        }
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        }

        sensorManager.registerListener(
            sensorListener,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        acceleration = 10f
        currentAcceleration = SensorManager.GRAVITY_EARTH
        lastAcceleration = SensorManager.GRAVITY_EARTH
    }
}