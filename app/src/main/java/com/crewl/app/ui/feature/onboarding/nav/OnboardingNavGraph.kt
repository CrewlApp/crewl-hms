package com.crewl.app.ui.feature.onboarding.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.crewl.app.ui.feature.NavGraphs
import com.crewl.app.ui.feature.onboarding.ui.OnboardingScreen
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun OnboardingNavGraph() {
    DestinationsNavHost(navGraph = NavGraphs.root)
}