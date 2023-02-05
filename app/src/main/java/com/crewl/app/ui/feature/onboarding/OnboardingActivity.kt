package com.crewl.app.ui.feature.onboarding

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.crewl.app.ui.feature.onboarding.nav.OnboardingNavGraph
import com.crewl.app.ui.provider.LanguageProvider
import com.crewl.app.ui.theme.CrewlColors
import com.crewl.app.ui.theme.CrewlTheme
import com.crewl.app.utils.SetupSystemUi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingActivity : FragmentActivity() {
    @Inject
    lateinit var languageProvider: LanguageProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            languageProvider.setLocale(languageProvider.getLanguageCode(), LocalContext.current)
            OnboardingRoot()
        }
    }
}

@Composable
private fun OnboardingRoot() {
    CrewlTheme {
        SetupSystemUi(systemUiController = rememberSystemUiController(), systemColor = CrewlColors.primary)
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CrewlColors.background
        ) {
            OnboardingNavGraph()
        }
    }
}