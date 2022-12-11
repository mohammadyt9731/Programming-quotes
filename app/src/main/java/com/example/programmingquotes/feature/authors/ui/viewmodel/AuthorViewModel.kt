package com.example.programmingquotes.feature.authors.ui.viewmodel

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.authors.data.repository.AuthorRepository
import com.example.programmingquotes.feature.authors.ui.model.AuthorView
import com.example.programmingquotes.feature.quote.ui.model.QuoteView
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorViewModel @Inject constructor(
    private val repository: AuthorRepository,
    @ApplicationContext context: Context
) :
    ViewModel() {

    private var sensorListener: SensorEventListener? = null
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val _isShakeAndShowQuote = MutableStateFlow(false)
    val isShakeAndShowQuote: StateFlow<Boolean> = _isShakeAndShowQuote
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _isLoadingBottomSheet = MutableStateFlow(false)
    val isLoadingBottomSheet: StateFlow<Boolean> = _isLoadingBottomSheet
    private val _authors = MutableStateFlow<List<AuthorView>>(emptyList())
    val authors: StateFlow<List<AuthorView>> = _authors

    private val _randomQuote = MutableStateFlow<QuoteView>(
        QuoteView(
            id = "",
            author = "",
            quote = ""
        )
    )
    val randomQuote: StateFlow<QuoteView> = _randomQuote

    init {
        getAuthorsFromApiAndInsertToDb()
        getAuthors()
    }

    private fun getAuthorsFromApiAndInsertToDb() {
        viewModelScope.launch {
            when (val response = repository.getAuthorsFromApiAndInsertToDb()) {
                is ResultWrapper.Success -> {
                    _isLoading.value = false
                    Log.i(
                        "TAG",
                        "getAuthorsFromApiAndInsertToDb: success"
                    )
                }
                is ResultWrapper.ApplicationError -> {
                    Log.i("TAG", "getAuthorsFromApiAndInsertToDb: app error${response.message}")
                }
                is ResultWrapper.HttpError -> {
                    Log.i("TAG", "getAuthorsFromApiAndInsertToDb: http error")
                }
                is ResultWrapper.NetworkError -> {
                    _isLoading.value = false
                }
            }
        }
    }

    private fun getAuthors() = viewModelScope.launch(Dispatchers.IO) {
        repository.getAuthors().collect {
            _authors.value = it
        }
    }

    fun getRandomQuoteFromApi() = viewModelScope.launch(Dispatchers.IO) {
        when (val response = repository.getRandomQuoteFromApi()) {
            is ResultWrapper.Success -> {
                _isLoadingBottomSheet.value = false
                _isShakeAndShowQuote.value = true
                response.data?.let {
                    _randomQuote.value = it
                }
            }
            is ResultWrapper.HttpError -> {}
            is ResultWrapper.ApplicationError -> {}
            is ResultWrapper.NetworkError -> {
                _isLoadingBottomSheet.value = false
                _isShakeAndShowQuote.value = true
                repository.getRandomQuote().collect {
                    _randomQuote.value = it
                }
            }
        }
    }

    fun startSensorManager() {
        setUpSensorManager()
    }

    fun stopSensorManager() {
        sensorManager.unregisterListener(sensorListener)
        _isShakeAndShowQuote.value = false
    }

    private fun setUpSensorManager() {

        var acceleration = 0f
        var currentAcceleration = 0f
        var lastAcceleration = 0f

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
                    if (acceleration > 35) {
                        if (!_isLoadingBottomSheet.value) {
                            _isLoadingBottomSheet.value = true
                            getRandomQuoteFromApi()
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