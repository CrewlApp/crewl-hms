package com.crewl.app.di

import android.annotation.SuppressLint
import android.content.Context
import com.crewl.app.data.repository.AuthenticationRepositoryImpl
import com.crewl.app.data.repository.LoginRepositoryImpl
import com.crewl.app.data.repository.OnboardingRepositoryImpl
import com.crewl.app.data.source.local.CountryDataSource
import com.crewl.app.domain.repository.FirebaseUserActions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@SuppressLint("VisibleForTests")
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideOnboardingRepository(@ApplicationContext context: Context) = OnboardingRepositoryImpl(context)

    @Singleton
    @Provides
    fun provideLoginRepository(countryDataSource: CountryDataSource) = LoginRepositoryImpl(countryDataSource = countryDataSource)

    @Singleton
    @Provides
    fun provideAuthenticationRepository(firebaseUserActions: FirebaseUserActions) =
        AuthenticationRepositoryImpl(firebaseUserActions = firebaseUserActions)
}