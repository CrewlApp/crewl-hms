package com.crewl.app.ui.feature.onboarding.nav

sealed class OnboardingRouter(val route: String) {
    object Onboarding: OnboardingRouter(route = "onboarding_screen")
    object Empty: OnboardingRouter(route = "empty")
}