/**
 * @author Kaan Fırat
 *
 * @since 1.0
 */

package com.crewl.app.di

import android.content.Context
import com.crewl.app.framework.preferences.CacheManager
import com.crewl.app.provider.AppLanguageProvider
import com.crewl.app.provider.AppResourceProvider
import com.crewl.app.provider.AppThemeProvider
import com.crewl.app.ui.provider.LanguageProvider
import com.crewl.app.ui.provider.ResourceProvider
import com.crewl.app.ui.provider.ThemeProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProviderModule {
    @Provides
    @Singleton
    fun provideThemeProvider(@ApplicationContext context: Context): ThemeProvider = AppThemeProvider(context)

    @Provides
    @Singleton
    fun provideAppResourceProvider(): ResourceProvider = AppResourceProvider()

    @Provides
    @Singleton
    fun provideAppLanguageProvider(cacheManager: CacheManager): LanguageProvider = AppLanguageProvider(cacheManager)
}