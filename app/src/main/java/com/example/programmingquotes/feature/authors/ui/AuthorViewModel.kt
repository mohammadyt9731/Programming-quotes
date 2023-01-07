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
import kotlinx.coroutines.flow.catch
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
            is AuthorAction.RefreshAuthors -> {
                updateAuthors()
            }
            is AuthorAction.StartSensorManager -> {
                startSensorManager()
            }
            is AuthorAction.StopSensorManager -> {
                stopSensorManager()
            }
        }
    }

    private fun getAuthors() = viewModelScope.launch(Dispatchers.IO) {
        _viewState.emit(_viewState.value.copy(authorsState = ResultWrapper.Loading))
        repository.getAuthors()
            .catch { errorChannel.send(it.message.toString()) }
            .collect {
                if (it.isEmpty()) {
                    updateAuthors()
                }
                _viewState.emit(
                    _viewState.value.copy(authorsState = ResultWrapper.Success(it))
                )
            }
    }

    private fun updateAuthors() = viewModelScope.launch(Dispatchers.IO) {
        _viewState.emit(_viewState.value.copy(updateState = ResultWrapper.Loading))
        val response = repository.fetchAuthorsAndInsertToDb()
        _viewState.emit(_viewState.value.copy(updateState = response))
        if (response is ResultWrapper.Error) {
            errorChannel.send(response.errors.message)

        }
    }

    private fun getRandomQuote() = viewModelScope.launch(Dispatchers.IO) {
        _viewState.emit(_viewState.value.copy(bottomSheetState = ResultWrapper.Loading))
        repository.getRandomQuote()
            .collect {
                if (it is ResultWrapper.Error) {
                    errorChannel.send(it.errors.message)
                }
                _viewState.emit(
                    _viewState.value.copy(bottomSheetState = it)
                )
            }
        isNextRequestReady = true
    }

    //sensor
    private fun startSensorManager() = setUpSensorManager()

    private fun stopSensorManager() = viewModelScope.launch(Dispatchers.IO) {
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

                viewModelScope.launch(Dispatchers.IO) {
                    if (acceleration > 12) {
                        if (isNextRequestReady) {
                            isNextRequestReady = false
                            getRandomQuote()
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