package com.crewl.app.ui.feature.onboarding.nav

import android.view.View
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.flowOf
import java.util.concurrent.Flow

sealed class OnboardingRouter(val route: String) {
    object Onboarding: OnboardingRouter(route = "onboarding_screen")
    object Empty: OnboardingRouter(route = "empty")
}

interface SplashScreenContract {
    fun navigate()

    fun redirectLogin()

    fun redirectRegister()

    fun redirectMessages()
}

/**
 * View - SplashScreen
 * ViewModel - SplashViewModel [Business Logic]
 * Router - SplashRouter
 */


