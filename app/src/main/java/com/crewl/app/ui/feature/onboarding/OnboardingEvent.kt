package com.crewl.app.ui.feature.onboarding

sealed class OnboardingEvent {
    object Navigate : OnboardingEvent()
}