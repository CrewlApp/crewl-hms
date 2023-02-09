package com.crewl.app.ui.router

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.crewl.app.ui.feature.login.LoginScreen
import com.crewl.app.ui.feature.onboarding.OnboardingScreen
import com.crewl.app.ui.feature.prehome.PreHomeScreen

@Composable
@ExperimentalMaterialApi
fun SetupNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.OnboardingScreen.route,
        builder = {
            composable(route = Screen.OnboardingScreen.route) {
                OnboardingScreen(navigator = navController)
            }

            composable(route = Screen.PreHomeScreen.route) {
                PreHomeScreen(navController)
            }

            composable(route = Screen.LoginScreen.route) {
                LoginScreen(navigator = navController)
            }
        }
    )
}