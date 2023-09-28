package com.crewl.app.di

import com.crewl.app.data.repository.FirebaseUserActionsImpl
import com.crewl.app.domain.repository.FirebaseUserActions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {
    @Singleton
    @Provides
    fun provideFirebaseUserActions(auth: FirebaseAuth, firestore: FirebaseFirestore): FirebaseUserActions =
        FirebaseUserActionsImpl(auth = auth, firestore = firestore)

    @Singleton
    @Provides
    fun provideAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
}