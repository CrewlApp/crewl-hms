package com.crewl.app.data.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.crewl.app.CrewlApplication
import com.crewl.app.data.model.user.PhoneNumber
import com.crewl.app.domain.repository.FirebaseUserActions
import com.crewl.app.framework.base.IOTaskResult
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.io.IOException
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
                        throw task.exception!!
                    } catch (invalidEmail: FirebaseAuthInvalidUserException) {
                        Log.d("App.tag", "onComplete: invalid_email")
                    }
                }
            }.await()
    }

    override suspend fun createUserWithEmailAndPassword(mail: String, password: String) {

    }

    override suspend fun isUserExists(number: String) = callbackFlow {
        firestore.collection("users").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result.forEach { document ->
                    val numberHolder = document.getString("number")

                    if (number == numberHolder) {
                        trySend(IOTaskResult.OnSuccess(true))
                    } else {
                        trySend(IOTaskResult.OnSuccess(false))
                    }
                }
            } else {
                Timber.tag("App.tag").d("User not exists.")
            }
        }.addOnFailureListener {
                trySend(IOTaskResult.OnFailed(IOException("Process failed.")))
        }.addOnCanceledListener {
            trySend(IOTaskResult.OnFailed(IOException("Process failed.")))
        }

        awaitClose()
    }
}