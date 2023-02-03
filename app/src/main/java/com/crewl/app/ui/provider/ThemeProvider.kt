package com.crewl.app.ui.provider

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.Flow

interface ThemeProvider {
    var theme: Theme
    fun observeTheme(): Flow<Theme>

    fun isNightMode(): Boolean

    fun setNightMode(forceNight: Boolean)

    enum class Theme {
        LIGHT,
        DARK,
        SYSTEM
    }
}

@Composable
fun ThemeProvider.shouldUseDarkMode(): Boolean {
    val themePreference = observeTheme().collectAsState(initial = ThemeProvider.Theme.SYSTEM)
    val mode = when (themePreference.value) {
        ThemeProvider.Theme.LIGHT -> false
        ThemeProvider.Theme.DARK -> true
        else -> isSystemInDarkTheme()
    }
    setNightMode(mode)
    return mode
}