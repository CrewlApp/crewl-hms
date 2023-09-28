package com.crewl.app.framework.base

/**
 * Sealed class type-restricts the result of IO calls to success and failure. The type
 * represents the model class expected from the API/Firebase call in case of a success.
 * <p>
 * @since 1.0
 */
sealed class IOTaskResult<out O : Any> {
    /**
     * In case of success, the result will be wrapped around the OnSuccessResponse class.
     */
    data class OnSuccess<out O : Any>(val data: O) : IOTaskResult<O>()

    /**
     * In case of error, the throwable causing the error will be wrapped around OnErrorResponse class.
     */
    data class OnFailed(val throwable: Throwable) : IOTaskResult<Nothing>()
}