package com.crewl.app.domain.usecase.authentication

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.crewl.app.data.model.user.PhoneNumber
import com.crewl.app.domain.repository.AuthenticationRepository
import com.crewl.app.domain.repository.LoginRepository
import com.crewl.app.framework.base.IOTaskResult
import com.crewl.app.framework.network.DataState
import com.crewl.app.framework.usecase.DataStateUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class CheckUserUseCase @Inject constructor
    (@get: VisibleForTesting(otherwise = VisibleForTesting.PROTECTED) internal val repository: AuthenticationRepository) {
    data class Params(
        val number: PhoneNumber? = null
    )

    suspend fun execute(number: String): Flow<IOTaskResult<Boolean>> = repository.isUserExists(number = number)
}