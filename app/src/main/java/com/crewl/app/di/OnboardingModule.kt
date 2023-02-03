package com.crewl.app.di

import android.annotation.SuppressLint
import com.crewl.app.data.repository.OnboardingRepository
import com.crewl.app.domain.usecase.onboarding.ReadOnboardingUseCase
import com.crewl.app.domain.usecase.onboarding.SaveOnboardingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@SuppressLint("VisibleForTests")
@Module
@InstallIn(SingletonComponent::class)
class OnboardingModule {
    @Singleton
    @Provides
    fun provideSaveOnboardingUseCase(repository: OnboardingRepository): SaveOnboardingUseCase = SaveOnboardingUseCase(repository = repository)

    @Singleton
    @Provides
    fun provideReadOnboardingUseCase(repository: OnboardingRepository): ReadOnboardingUseCase = ReadOnboardingUseCase(repository = repository)
}