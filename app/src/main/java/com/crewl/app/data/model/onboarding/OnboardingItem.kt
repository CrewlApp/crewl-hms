/**
 * @author Kaan Fırat
 *
 * @since 1.0
 */

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
        title = getString(R.string.EXPLORE_EVENTS_AROUND),
        description = getString(R.string.CREATE_EVENTS_FOR_PEOPLE_OR_JOIN_ORGANIZED_EVENTS)
    )

    object Second: OnboardingItem(
        image = R.drawable.img_second_onboarding,
        title = getString(R.string.MEET_NEW_PEOPLE_AND_COMPETE),
        description = getString(R.string.FIND_NEW_PEOPLE_IN_YOUR_CITY_CREATE_YOUR_TEAM_AT_EVENTS)
    )

    object Third: OnboardingItem(
        image = R.drawable.img_third_onboarding,
        title = getString(R.string.GET_SPECIAL_DISCOUNTS_AND_TRY_DIFFERENT_FLAVORS),
        description = getString(R.string.TRY_SPECIAL_DRINKS_OF_THE_BARS_YOU_VISIT)
    )

    object Fourth: OnboardingItem(
        image = R.drawable.img_fourth_onboarding,
        title = getString(R.string.EXPERIENCE_AN_UNFORGETTABLE_NIGHT),
        description = getString(R.string.JOIN_NOW_AND_BE_PART_OF_THE_STORY)
    )
}