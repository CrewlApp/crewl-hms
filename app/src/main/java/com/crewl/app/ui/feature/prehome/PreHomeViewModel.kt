package com.crewl.app.ui.feature.prehome

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.crewl.app.domain.usecase.onboarding.ReadOnboardingUseCase
import com.crewl.app.domain.usecase.onboarding.SaveOnboardingUseCase
import com.crewl.app.framework.base.BaseViewModel
import com.crewl.app.ui.feature.onboarding.nav.OnboardingRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreHomeViewModel @Inject constructor(
    private val saveOnboardingUseCase: SaveOnboardingUseCase,
    private val readOnboardingUseCase: ReadOnboardingUseCase
) : BaseViewModel() {
    private val _startDestination: MutableState<String> = mutableStateOf(OnboardingRouter.Onboarding.route)
    val startDestination: State<String> = _startDestination

    fun saveOnboardingState(isCompleted: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        val params = SaveOnboardingUseCase.Params(isCompleted = isCompleted)
        call(saveOnboardingUseCase(params = params))
    }

    fun readOnboardingState() = viewModelScope.launch {
        readOnboardingUseCase(Unit).collect { isCompleted ->
            if (isCompleted)
                _startDestination.value = OnboardingRouter.Empty.route
            else
                _startDestination.value = OnboardingRouter.Onboarding.route
        }
    }
}