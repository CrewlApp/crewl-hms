package com.crewl.app.data.repository

import android.util.Log
import com.crewl.app.data.model.user.PhoneNumber
import com.crewl.app.domain.repository.AuthenticationRepository
import com.crewl.app.domain.repository.FirebaseUserActions
import com.crewl.app.framework.base.IOTaskResult
import com.crewl.app.framework.base.ViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(private val firebaseUserActions: FirebaseUserActions): AuthenticationRepository {
    override suspend fun signIn(number: PhoneNumber): Flow<FirebaseUser> = flow {  }

    override suspend fun signUp(number: PhoneNumber): Flow<FirebaseUser> = flow {  }

    override suspend fun isUserExists(number: String): Flow<IOTaskResult<Boolean>> = firebaseUserActions.isUserExists(number = number)
}