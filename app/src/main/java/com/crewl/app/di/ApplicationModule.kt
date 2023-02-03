package com.crewl.app.di

import android.content.Context
import com.crewl.app.CrewlApplication
import com.crewl.app.MainNetworkConfig
import com.crewl.app.framework.app.AppInitializer
import com.crewl.app.framework.app.AppInitializerImpl
import com.crewl.app.framework.app.NetworkConfig
import com.crewl.app.framework.app.TimberInitializer
import com.crewl.app.framework.preferences.CacheManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Provides
    @Singleton
    fun provideApplication(): CrewlApplication = CrewlApplication()

    @Provides
    @Singleton
    fun provideAppInitializer(timberInitializer: TimberInitializer): AppInitializer = AppInitializerImpl(timberInitializer)

    @Provides
    @Singleton
    fun provideNetworkConfig(): NetworkConfig = MainNetworkConfig()

    @Provides
    @Singleton
    fun provideTimberInitializer(networkConfig: NetworkConfig) = TimberInitializer(networkConfig.isDev())

    @Provides
    @Singleton
    fun provideCacheManager(@ApplicationContext context: Context): CacheManager = CacheManager(context)
}