package com.example.programmingquotes.feature.authors.ui.viewmodel

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingquotes.R
import com.example.programmingquotes.core.common.ErrorType
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.data.network.NetworkConnectivity
import com.example.programmingquotes.feature.authors.data.repository.AuthorRepository
import com.example.programmingquotes.feature.authors.ui.model.AuthorView
import com.example.programmingquotes.feature.quote.ui.model.QuoteView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AuthorViewModel @Inject constructor(
    private val repository: AuthorRepository,
    private val networkConnectivity: NetworkConnectivity,
    private val sensorManager: SensorManager
) : ViewModel() {

    private val _pageState =
        MutableStateFlow<ResultWrapper<Unit>>(ResultWrapper.UnInitialize)
    val pageState: StateFlow<ResultWrapper<Unit>> = _pageState
    private val _authors =
        MutableStateFlow<List<AuthorView>>(emptyList())
    val authors: StateFlow<List<AuthorView>> = _authors

    // bottom sheet
    private var sensorListener: SensorEventListener? = null
    private val _isShakePhone = MutableStateFlow(false)
    val isShakePhone: StateFlow<Boolean> = _isShakePhone
    private var isNextRequestReady = true
    private val _pageStateBottomSheet =
        MutableStateFlow<ResultWrapper<QuoteView?>>(ResultWrapper.UnInitialize)
    val pageStateBottomSheet: StateFlow<ResultWrapper<QuoteView?>> = _pageStateBottomSheet

    init {
        getAuthors()
    }

    private fun getAuthors() = viewModelScope.launch(Dispatchers.IO) {
        repository.getAuthors().collect {
            if (it.isEmpty()) {
                fetchAuthorsAndInsertToDb()
            } else {
                _authors.emit(it)
            }
        }
    }

    fun fetchAuthorsAndInsertToDb() {
        viewModelScope.launch(Dispatchers.IO) {
            if (networkConnectivity.isNetworkConnected()) {
                _pageState.emit(ResultWrapper.Loading)

                _pageState.emit(repository.fetchAuthorsAndInsertToDb())
            } else {
                _pageState.emit(
                    ResultWrapper.Error(
                        type = ErrorType.NETWORK,
                        stringResId = R.string.msg_no_internet
                    )
                )
            }
        }
    }

    private fun fetchRandomQuote() = viewModelScope.launch(Dispatchers.IO) {
        if (networkConnectivity.isNetworkConnected()) {
            _pageStateBottomSheet.emit(ResultWrapper.Loading)
            _pageStateBottomSheet.emit(repository.fetchRandomQuote()).also {
                _isShakePhone.emit(true)
                isNextRequestReady = true
            }
        } else {
            _pageStateBottomSheet.emit(
                ResultWrapper.Error(
                    type = ErrorType.NETWORK,
                    stringResId = R.string.msg_no_internet
                )
            )
            repository.getRandomQuote().collect {
                it?.let {
                    _pageStateBottomSheet.emit(ResultWrapper.Success(it)).also {
                        isNextRequestReady = true
                        _isShakePhone.emit(true)
                    }
                }
            }
        }
    }

    fun resetPageStateBottomSheet() {
        viewModelScope.launch {
            _pageStateBottomSheet.emit(ResultWrapper.UnInitialize)
        }
    }

    fun startSensorManager() = setUpSensorManager()

    suspend fun stopSensorManager() {
        sensorManager.unregisterListener(sensorListener)
        isNextRequestReady = true
        _isShakePhone.emit(false)
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
                    if (acceleration > 15) {
                        if (isNextRequestReady) {
                            isNextRequestReady = false
                            fetchRandomQuote()
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