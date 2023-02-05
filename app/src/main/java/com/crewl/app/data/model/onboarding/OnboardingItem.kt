package com.crewl.app.data.model.onboarding

import androidx.annotation.DrawableRes
import com.crewl.app.R
import com.crewl.app.framework.extension.getString

sealed class OnboardingItem(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
) {
    object First: OnboardingItem(
        image = R.drawable.img_first_onboarding,
        title = getString(R.string.onboarding_title_first),
        description = getString(R.string.onboarding_description_first)
    )

    object Second: OnboardingItem(
        image = R.drawable.img_second_onboarding,
        title = getString(R.string.onboarding_title_second),
        description = getString(R.string.onboarding_description_second)
    )

    object Third: OnboardingItem(
        image = R.drawable.img_third_onboarding,
        title = getString(R.string.onboarding_title_third),
        description = getString(R.string.onboarding_description_third)
    )

    object Fourth: OnboardingItem(
        image = R.drawable.img_fourth_onboarding,
        title = getString(R.string.onboarding_title_fourth),
        description = getString(R.string.onboarding_description_fourth)
    )
}