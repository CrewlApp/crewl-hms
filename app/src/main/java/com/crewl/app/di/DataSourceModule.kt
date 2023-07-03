package com.crewl.app.di

import android.content.Context
import com.crewl.app.data.source.local.CountryDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Provides
    @Singleton
    fun provideCountryDataSource(@ApplicationContext context: Context): CountryDataSource = CountryDataSource(context)
}