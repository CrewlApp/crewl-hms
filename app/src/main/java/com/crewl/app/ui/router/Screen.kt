package com.crewl.app.ui.router

sealed class Screen(val route: String) {
    object SplashScreen : Screen(route = Route.Splash)
    object OnboardingScreen : Screen(route = Route.Onboarding)
    object LoginScreen : Screen(route = Route.Login)
    object AuthenticationScreen : Screen(route = Route.Authentication)
    object PreRegisterScreen : Screen(route = Route.PreRegister)
    object RegisterScreen : Screen(route = Route.Register)
    object HomeScreen : Screen(route = Route.Home)
}