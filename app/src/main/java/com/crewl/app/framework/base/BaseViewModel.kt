/**
 * @author Kaan Fırat
 *
 * @since 1.0
 */

package com.crewl.app.framework.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crewl.app.framework.network.DataState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber

abstract class BaseViewModel: ViewModel() {
    companion object {
        private const val SAFE_LAUNCH_EXCEPTION = "ViewModel exception handler."
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        Timber.tag(SAFE_LAUNCH_EXCEPTION).e(exception)
        handleError(exception)
    }

    open fun handleError(exception: Throwable) {}

    open fun startLoading() {}

    protected fun safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(handler, block = block)
    }

    protected suspend fun <T> call(
        callFlow: Flow<T>,
        completionHandler: (collect: T) -> Unit = {}
    ) {
        callFlow
            .catch { handleError(it) }
            .collect {
                completionHandler.invoke(it)
            }
    }

    suspend fun <T : Any> call(IO: suspend () -> Flow<IOTaskResult<T>>) =
        flow {
            emit(ViewState.Loading(true))
            IO().map {
                when (it) {
                    is IOTaskResult.OnSuccess -> ViewState.RenderSuccess(it.data)
                    is IOTaskResult.OnFailed -> ViewState.RenderFailure(it.throwable)
                }
            }.collect {
                emit(it)
            }
            emit(ViewState.Loading(false))
        }.flowOn(Dispatchers.IO)

    protected suspend fun <T> execute(
        callFlow: Flow<DataState<T>>,
        completionHandler: (collect: T) -> Unit = {}
    ) {
        callFlow
            .onStart { startLoading() }
            .catch { handleError(it) }
            .collect { state ->
                when (state) {
                    is DataState.Error -> handleError(state.error)
                    is DataState.Success -> completionHandler.invoke(state.result)
                    is DataState.Loading -> {}
                }
            }
    }
}