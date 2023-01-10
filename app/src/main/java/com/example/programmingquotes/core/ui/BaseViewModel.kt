package com.example.programmingquotes.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal open class BaseViewModel<S, A> @Inject constructor(initializeState: S) : ViewModel() {

    private val _viewState = MutableStateFlow(initializeState)
    val viewState = _viewState.asStateFlow()

    private val errorChannel = Channel<String>()

    protected fun setError(errorMessage: String) = viewModelScope.launch {
        errorChannel.send(errorMessage)
    }

    fun errorChannelFlow() = errorChannel.receiveAsFlow()

    protected fun setState(state: S.() -> S) = viewModelScope.launch {
        _viewState.emit(state(_viewState.value))
    }
}