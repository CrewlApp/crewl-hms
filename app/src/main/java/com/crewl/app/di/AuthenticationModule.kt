package com.crewl.app.di

import android.annotation.SuppressLint
import com.crewl.app.data.repository.AuthenticationRepositoryImpl
import com.crewl.app.data.repository.LoginRepositoryImpl
import com.crewl.app.domain.usecase.authentication.CheckUserUseCase
import com.crewl.app.domain.usecase.authentication.SignInUseCase
import com.crewl.app.domain.usecase.authentication.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@SuppressLint("VisibleForTests")
@Module
@InstallIn(SingletonComponent::class)
class AuthenticationModule {
    @Singleton
    @Provides
    fun provideSignInUseCase(repository: AuthenticationRepositoryImpl): SignInUseCase = SignInUseCase(repository = repository)

    @Singleton
    @Provides
    fun provideSignUpUseCase(repository: AuthenticationRepositoryImpl): SignUpUseCase = SignUpUseCase(repository = repository)

    @Singleton
    @Provides
    fun provideCheckUserUseCase(repository: AuthenticationRepositoryImpl): CheckUserUseCase = CheckUserUseCase(repository = repository)
}