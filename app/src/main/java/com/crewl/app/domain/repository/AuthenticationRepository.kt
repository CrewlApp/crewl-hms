package com.crewl.app.domain.repository

import com.crewl.app.data.model.user.PhoneNumber
import com.crewl.app.framework.base.IOTaskResult
import com.crewl.app.framework.network.DataState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    suspend fun signIn(number: PhoneNumber): Flow<FirebaseUser>

    suspend fun signUp(number: PhoneNumber): Flow<FirebaseUser>

    suspend fun isUserExists(number: String): Flow<IOTaskResult<Boolean>>
}