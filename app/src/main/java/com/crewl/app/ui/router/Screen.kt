package com.crewl.app.ui.router

sealed class Screen(val route: String) {
    object SplashScreen : Screen(route = Route.Splash)
    object OnboardingScreen : Screen(route = Route.Onboarding)
    object PreHomeScreen : Screen(route = Route.PreHome)
    object LoginScreen : Screen(route = Route.Login)
    object AuthenticationLoginScreen : Screen(route = Route.AuthenticationLogin)
}