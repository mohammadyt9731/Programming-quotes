package com.example.programmingquotes.feature.authors.ui

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.viewModelScope
import com.example.programmingquotes.core.common.Errors
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.ui.BaseViewModel
import com.example.programmingquotes.feature.authors.domain.usecase.GetAuthorsUseCase
import com.example.programmingquotes.feature.authors.domain.usecase.GetRandomQuoteUseCase
import com.example.programmingquotes.feature.authors.domain.usecase.UpdateAuthorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AuthorViewModel @Inject constructor(
    private val getAuthorsUseCase: GetAuthorsUseCase,
    private val updateAuthorsUseCase: UpdateAuthorsUseCase,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase,
    private val sensorManager: SensorManager
) : BaseViewModel<AuthorViewState, AuthorAction>(AuthorViewState()) {

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
                setUpSensorManager()
            }
            is AuthorAction.StopSensorManager -> {
                stopSensorManager()
            }
        }
    }

    private fun getAuthors() = viewModelScope.launch(Dispatchers.IO) {
        setState { copy(authors = ResultWrapper.Loading) }
        getAuthorsUseCase()
            .catch {
                setError(it.message.toString())
                setState {
                    copy(
                        authors = ResultWrapper.Error(
                            Errors.App(msg = it.message.toString())
                        )
                    )
                }
            }
            .collect {
                if (it.isEmpty()) {
                    updateAuthors()
                }
                setState {
                    copy(authors = ResultWrapper.Success(it))
                }
            }
    }

    private fun updateAuthors() = viewModelScope.launch(Dispatchers.IO) {
        setState { copy(update = ResultWrapper.Loading) }
        val response = updateAuthorsUseCase()
        setState { copy(update = response) }
        if (response is ResultWrapper.Error) {
            setError(response.errors.message)

        }
    }

    private fun getRandomQuote() = viewModelScope.launch(Dispatchers.IO) {
        setState { copy(bottomSheet = ResultWrapper.Loading) }
        getRandomQuoteUseCase()
            .collect {
                if (it is ResultWrapper.Error) {
                    setError(it.errors.message)
                }
                setState {
                    copy(bottomSheet = it)
                }
            }
        isNextRequestReady = true
    }


    //sensor
    private fun stopSensorManager() = viewModelScope.launch(Dispatchers.IO) {
        sensorManager.unregisterListener(sensorListener)
        setState { copy(bottomSheet = ResultWrapper.UnInitialize) }
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