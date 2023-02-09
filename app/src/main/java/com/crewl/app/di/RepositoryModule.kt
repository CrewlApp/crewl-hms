package com.crewl.app.di

import android.annotation.SuppressLint
import android.content.Context
import com.crewl.app.data.repository.OnboardingRepositoryImpl
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
}