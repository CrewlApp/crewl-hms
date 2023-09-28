package com.crewl.app.ui.feature.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crewl.app.domain.usecase.onboarding.ReadOnboardingUseCase
import com.crewl.app.framework.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SplashViewModel @Inject constructor(private val readOnboardingUseCase: ReadOnboardingUseCase): BaseViewModel() {
    private val _startOnboarding = MutableStateFlow(false)
    val startOnboarding = _startOnboarding.asStateFlow()

    private val _isSplashScreenLoaded: MutableState<Boolean> = mutableStateOf(true)

    init {
        viewModelScope.launch {
            delay(3000)
            _isSplashScreenLoaded.value = false
        }

        readOnBoardingState()
    }

    private fun readOnBoardingState() = safeLaunch {
        call(readOnboardingUseCase(Unit)) { isCompleted ->
            _startOnboarding.value = !isCompleted
        }
    }
}
