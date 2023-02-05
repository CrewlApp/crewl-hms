package com.crewl.app.ui.feature.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.compose.*
import com.crewl.app.R
import com.crewl.app.framework.extension.launchActivity
import com.crewl.app.ui.router.Screen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

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

        lifecycleScope.launchWhenCreated {
            viewModel.startOnboarding.collectLatest {
                delay(3000)
                navigate(packageName = packageName, className = Screen.Onboarding)
            }
        }
    }

    private fun navigate(packageName: String, className: String) {
        launchActivity(packageName = packageName, className = className).also {
            finish()
        }
    }

    @Composable
    private fun LottieLogo() {
        val compositionResult: LottieCompositionResult = rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.logo_lottie_animated)
        )

        val progressAnimation by animateLottieCompositionAsState(
            compositionResult.value,
            isPlaying = true,
            iterations = LottieConstants.IterateForever,
            speed = 1.0f
        )

        LottieAnimation(composition = compositionResult.value, progress = progressAnimation)
    }
}

