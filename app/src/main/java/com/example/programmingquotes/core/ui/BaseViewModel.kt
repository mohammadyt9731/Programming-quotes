package com.example.programmingquotes.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingquotes.core.common.Errors
import com.example.programmingquotes.core.common.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject
import kotlin.coroutines.EmptyCoroutineContext

internal open class BaseViewModel<S, A> @Inject constructor(initializeState: S) : ViewModel() {

    private val _viewState = MutableStateFlow(initializeState)
    val viewState = _viewState.asStateFlow()

    private val errorChannel = Channel<String>()

    private fun setError(errorMessage: String) = viewModelScope.launch {
        errorChannel.send(errorMessage)
    }

    fun errorChannelFlow() = errorChannel.receiveAsFlow()

    protected fun setState(state: S.() -> S) = viewModelScope.launch {
        _viewState.emit(state(_viewState.value))
    }

    protected open fun <T> Flow<T>.execute(
        dispatcher: CoroutineDispatcher? = null,
        reducer: S.(ResultWrapper<T>) -> S,
    ): Job {
        setState { reducer(ResultWrapper.Loading) }

        return catch { error ->
            setState { reducer(ResultWrapper.Error(Errors.App(msg = error.message.toString()))) }
            setError(error.message.toString())
        }
            .onEach { value -> setState { reducer(ResultWrapper.Success(value)) } }
            .launchIn(viewModelScope + (dispatcher ?: EmptyCoroutineContext))
    }

    protected open fun <T> Flow<ResultWrapper<T>>.executeOnResultWrapper(
        dispatcher: CoroutineDispatcher? = null,
        reducer: S.(ResultWrapper<T>) -> S,
    ): Job {
        setState { reducer(ResultWrapper.Loading) }

        return catch { error ->
            setState { reducer(ResultWrapper.Error(Errors.App(msg = error.message.toString()))) }
            setError(error.message.toString())
        }
            .onEach { value -> setState { reducer(value) } }
            .launchIn(viewModelScope + (dispatcher ?: EmptyCoroutineContext))
    }


    protected open fun <T : Any?> (suspend () -> ResultWrapper<T>).execute(
        dispatcher: CoroutineDispatcher? = null,
        reducer: S.(ResultWrapper<T>) -> S
    ): Job {
        setState { reducer(ResultWrapper.Loading) }

        return viewModelScope.launch(dispatcher ?: EmptyCoroutineContext) {
            val result = invoke()
            if (result is ResultWrapper.Error) {
                setState { reducer(ResultWrapper.Error(Errors.App(msg = result.errors.message))) }
                setError(result.errors.message)
            } else {
                setState { reducer(result) }
            }
        }
    }
}