package com.crewl.app.ui.feature.onboarding

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.crewl.app.ui.provider.LanguageProvider
import com.crewl.app.ui.router.SetupNavigationGraph
import com.crewl.app.ui.theme.CrewlColors
import com.crewl.app.ui.theme.CrewlTheme
import com.crewl.app.ui.theme.SoftPeach
import com.crewl.app.utils.SetupSystemUI
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingActivity : FragmentActivity() {
    @Inject
    lateinit var languageProvider: LanguageProvider

    private lateinit var navHostController: NavHostController

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            languageProvider.setLocale(languageProvider.getLanguageCode(), LocalContext.current)

            CrewlTheme {
                SetupSystemUI(systemUiController = rememberSystemUiController(), systemColor = SoftPeach)

                Surface(modifier = Modifier.fillMaxSize(), color = SoftPeach) {
                    navHostController = rememberNavController()
                    SetupNavigationGraph(navController = navHostController)
                }
            }
        }
    }
}