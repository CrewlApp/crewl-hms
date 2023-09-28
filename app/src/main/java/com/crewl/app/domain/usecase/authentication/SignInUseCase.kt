package com.crewl.app.domain.usecase.authentication

import androidx.annotation.VisibleForTesting
import com.crewl.app.data.model.user.PhoneNumber
import com.crewl.app.domain.repository.AuthenticationRepository
import com.crewl.app.domain.repository.LoginRepository
import com.crewl.app.framework.network.DataState
import com.crewl.app.framework.usecase.DataStateUseCase
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class SignInUseCase @Inject constructor
    (@get: VisibleForTesting(otherwise = VisibleForTesting.PROTECTED) internal val repository: AuthenticationRepository) :
    DataStateUseCase<SignInUseCase.Params, FirebaseUser>() {
    data class Params(
        val number: PhoneNumber? = null
    )

    override suspend fun FlowCollector<DataState<FirebaseUser>>.execute(params: Params) {
        params.number?.let { number ->
            repository.signIn(number = number)
        }
    }
}