package com.crewl.app.ui.feature.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.runtime.*
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.airbnb.lottie.compose.*
import com.crewl.app.framework.extension.launchActivity
import com.crewl.app.ui.feature.onboarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : FragmentActivity() {
    private val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                true
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.startOnboarding.collectLatest {
                    delay(3000)
                    navigate(packageName = packageName, className = OnboardingActivity::class.java.name)
                }
            }
        }
    }

    private fun navigate(packageName: String, className: String) {
        launchActivity(packageName = packageName, className = className).also {
            finish()
        }
    }
}

