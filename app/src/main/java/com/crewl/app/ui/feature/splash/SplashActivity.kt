package com.crewl.app.ui.feature.splash

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.crewl.app.framework.extension.launchActivity
import com.crewl.app.ui.theme.CrewlTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SplashActivity : FragmentActivity() {
    private val viewModel by viewModels<SplashViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
        }

        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.startOnboarding.collectLatest {
                delay(3000)
                navigate(packageName = packageName, className = "com.crewl.app.ui.feature.onboarding")
            }
        }
    }

    private fun navigate(packageName: String, className: String) {
        launchActivity(packageName = packageName, className = className).also {
            finish()
        }
    }
}

