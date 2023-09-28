package com.crewl.app.ui.feature.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.crewl.app.domain.usecase.onboarding.ReadOnboardingUseCase
import com.crewl.app.domain.usecase.onboarding.SaveOnboardingUseCase
import com.crewl.app.framework.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val saveOnboardingUseCase: SaveOnboardingUseCase
) : BaseViewModel() {
    val data by mutableStateOf(OnboardingState())

    private val _onboardingEventChannel: Channel<OnboardingEvent> = Channel()
    val onboardingEvent = _onboardingEventChannel.receiveAsFlow()

    fun saveOnboardingState(isCompleted: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        val params = SaveOnboardingUseCase.Params(isCompleted = isCompleted)
        call(saveOnboardingUseCase(params = params))
        onNavigate()
    }

    private fun onNavigate() = viewModelScope.launch(Dispatchers.IO) {
        _onboardingEventChannel.send(OnboardingEvent.Navigate)
    }
}