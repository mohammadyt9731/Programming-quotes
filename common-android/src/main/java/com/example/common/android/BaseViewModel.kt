package com.example.common.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.Errors
import com.example.base.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

open class BaseViewModel<S, A> @Inject constructor(initializeState: S) : ViewModel() {

    private val _viewState = MutableStateFlow(initializeState)
    val viewState = _viewState.asStateFlow()

    private val errorChannel = Channel<String>()

    private val actionSharedFlow = MutableSharedFlow<A>()

    private fun setError(errorMessage: String) = viewModelScope.launch {
        errorChannel.send(errorMessage)
    }

    fun errorChannelFlow() = errorChannel.receiveAsFlow()

    protected fun setState(state: S.() -> S) = viewModelScope.launch {
        _viewState.emit(state(_viewState.value))
    }

    fun setAction(action: A) = viewModelScope.launch {
        actionSharedFlow.emit(action)
    }

    protected fun onEachAction(
        action: suspend (A) -> Unit,
    ): Job = viewModelScope.launch {
        actionSharedFlow.collectLatest(action)
    }

    protected open fun <T> Flow<T>.execute(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        reducer: S.(ResultWrapper<T>) -> S,
    ): Job {
        setState { reducer(ResultWrapper.Loading) }

        return catch { error ->
            setState { reducer(ResultWrapper.Error(Errors.App(msg = error.message.toString()))) }
            setError(error.message.toString())
        }
            .onEach { value -> setState { reducer(ResultWrapper.Success(value)) } }
            .flowOn(dispatcher)
            .launchIn(viewModelScope)
    }

    protected open fun <T> Flow<ResultWrapper<T>>.executeOnResultWrapper(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        reducer: S.(ResultWrapper<T>) -> S,
    ): Job {
        setState { reducer(ResultWrapper.Loading) }

        return catch { error ->
            setState { reducer(ResultWrapper.Error(Errors.App(msg = error.message.toString()))) }
            setError(error.message.toString())
        }
            .onEach { value ->
                if (value is ResultWrapper.Error) {
                    setState { reducer(ResultWrapper.Error(Errors.App(msg = value.errors.message))) }
                    setError(value.errors.message)
                } else {
                    setState { reducer(value) }
                }
            }
            .flowOn(dispatcher)
            .launchIn(viewModelScope)
    }


    protected open fun <T : Any?> (suspend () -> ResultWrapper<T>).execute(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        reducer: S.(ResultWrapper<T>) -> S
    ): Job {
        setState { reducer(ResultWrapper.Loading) }

        return viewModelScope.launch(dispatcher) {
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