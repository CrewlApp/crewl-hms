package com.crewl.app.domain.repository

import com.crewl.app.data.model.user.PhoneNumber
import com.crewl.app.framework.base.IOTaskResult
import kotlinx.coroutines.flow.Flow

interface FirebaseUserActions {
    /** Get current user id. **/
    val currentUserId : String?

    /** Method for sign in to Firebase. **/
    suspend fun signInWithEmailAndPassword(mail: String, password: String)

    /** Method for create user to Firebase. **/
    suspend fun createUserWithEmailAndPassword(mail: String, password: String)

    suspend fun isUserExists(number: String): Flow<IOTaskResult<Boolean>>
}