package com.crewl.app.domain.usecase.authentication

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.crewl.app.data.model.user.PhoneNumber
import com.crewl.app.domain.repository.AuthenticationRepository
import com.crewl.app.domain.repository.LoginRepository
import com.crewl.app.framework.base.IOTaskResult
import com.crewl.app.framework.base.ViewState
import com.crewl.app.framework.network.DataState
import com.crewl.app.framework.usecase.DataStateUseCase
import com.crewl.app.framework.usecase.LocalUseCaseController
import com.crewl.app.framework.usecase.UseCaseController
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class CheckUserUseCase @Inject constructor
    (@get: VisibleForTesting(otherwise = VisibleForTesting.PROTECTED) internal val repository: AuthenticationRepository) :
    LocalUseCaseController<CheckUserUseCase.Params, IOTaskResult<Boolean>>() {
    data class Params(
        val number: PhoneNumber? = null
    )

    override suspend fun FlowCollector<IOTaskResult<Boolean>>.execute(params: Params) {
        repository.isUserExists(params.number?.withCountryCode ?: "").collect { result ->
            when (result) {
                is IOTaskResult.OnSuccess -> {
                    emit(IOTaskResult.OnSuccess(result.data))
                }
                is IOTaskResult.OnFailed -> {
                    emit(IOTaskResult.OnFailed(result.throwable))
                }
            }
        }
    }
}