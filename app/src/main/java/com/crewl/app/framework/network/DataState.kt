/**
 * @author Kaan Fırat
 *
 * @since 1.0
 */

package com.crewl.app.framework.network

sealed class DataState<out T> {
    data class Success<out T>(val result: T) : DataState<T>()
    data class Loading(val isLoading: Boolean) : DataState<Nothing>()
    data class Error(val error: Throwable) : DataState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$result]"
            is Error -> "Error[exception=$error]"
            is Loading -> "Loading[status=$isLoading]"
        }
    }
}
