package com.crewl.app.di

import android.annotation.SuppressLint
import com.crewl.app.data.repository.LoginRepositoryImpl
import com.crewl.app.domain.usecase.login.SearchCountryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@SuppressLint("VisibleForTests")
@Module
@InstallIn(SingletonComponent::class)
class LoginModule {
    @Singleton
    @Provides
    fun provideSearchCountryUseCase(repository: LoginRepositoryImpl): SearchCountryUseCase = SearchCountryUseCase(repository = repository)
}