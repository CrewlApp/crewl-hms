package com.crewl.app.ui.router

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.crewl.app.ui.feature.login.authentication.AuthenticationScreen
import com.crewl.app.ui.feature.login.number.LoginScreen
import com.crewl.app.ui.feature.login.LoginSharedViewModel
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
                OnboardingScreen(navigator = navController, viewModel = hiltViewModel())
            }

            composable(route = Screen.PreHomeScreen.route) {
                PreHomeScreen(navigator = navController, viewModel = hiltViewModel())
            }

            composable(route = Screen.LoginScreen.route) {
                LoginScreen(navigator = navController, viewModel = hiltViewModel())
            }

            composable(route = Screen.AuthenticationScreen.route) {
                val backStackEntry = remember(it) {
                    navController.getBackStackEntry(Screen.LoginScreen.route)
                }

                val viewModel: LoginSharedViewModel = hiltViewModel(backStackEntry)

                AuthenticationScreen(
                    navigator = navController,
                    viewModel = viewModel
                )
            }
        }
    )
}