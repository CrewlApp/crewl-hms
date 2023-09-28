package com.crewl.app.data.repository

import com.crewl.app.domain.repository.FirebaseUserActions
import com.crewl.app.framework.base.IOTaskResult
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserActionsImpl @Inject constructor(
    private var auth: FirebaseAuth,
    private var firestore: FirebaseFirestore
) : FirebaseUserActions {
    override val currentUserId: String?
        get() = auth.currentUser?.uid

    override suspend fun signInWithEmailAndPassword(mail: String, password: String) {
        auth.signInWithEmailAndPassword(mail, password)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    try {
                        throw task.exception ?: Exception()
                    } catch (invalidEmail: FirebaseAuthInvalidUserException) {
                        /* Empty. */
                    }
                }
            }.await()
    }

    override suspend fun createUserWithEmailAndPassword(mail: String, password: String) {
        /* Empty. */
    }

    override suspend fun isUserExists(number: String) = callbackFlow {
        val subscription = firestore.collection("users").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var userExists = false
                task.result?.forEach { document ->
                    val numberHolder = document.getString("number")
                    if (number == numberHolder) {
                        userExists = true
                        return@forEach
                    }
                }
                trySend(IOTaskResult.OnSuccess(userExists))
            } else {
                trySend(IOTaskResult.OnFailed(task.exception ?: Exception("Task is not successful.")))
            }
            close()
        }

        awaitClose { subscription.result }
    }
}