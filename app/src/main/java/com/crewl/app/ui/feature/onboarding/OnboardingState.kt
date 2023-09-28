package com.crewl.app.ui.feature.onboarding

import com.crewl.app.data.model.onboarding.OnboardingItem

data class OnboardingState(
    val items: List<OnboardingItem> = listOf(
        OnboardingItem.First,
        OnboardingItem.Second,
        OnboardingItem.Third,
        OnboardingItem.Fourth
    )
)