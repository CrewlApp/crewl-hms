package com.crewl.app.ui.router

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.crewl.app.data.model.user.PhoneNumber
import com.crewl.app.ui.feature.home.HomeScreen
import com.crewl.app.ui.feature.login.authentication.AuthenticationScreen
import com.crewl.app.ui.feature.login.number.LoginScreen
import com.crewl.app.ui.feature.login.LoginSharedViewModel
import com.crewl.app.ui.feature.onboarding.OnboardingScreen
import com.crewl.app.ui.feature.register.RegisterSharedScreen
import com.crewl.app.ui.feature.register.name.RegisterNameScreen
import com.crewl.app.ui.feature.register.preregister.PreRegisterScreen
import com.crewl.app.utils.getObject
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Sets up the navigation graph for the app using Jetpack Compose and Navigation Component.
 *
 * @param navController The [NavHostController] to manage navigation between screens.
 */
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

            composable(
                route = "${Screen.PreRegisterScreen.route}/{phoneNumber}"
            ) {
                val phoneNumberJson = it.arguments?.getString("phoneNumber")

                phoneNumberJson?.let {
                    PreRegisterScreen(
                        navigator = navController, phoneNumber = phoneNumberJson.getObject()
                    )
                }
            }

            composable(route = Screen.RegisterScreen.route) {
                RegisterSharedScreen(
                    navigator = navController,
                    viewModel = hiltViewModel()
                )
            }

            composable(route = Screen.HomeScreen.route) {
                HomeScreen(
                    navigator = navController,
                    viewModel = hiltViewModel()
                )
            }
        }
    )
}