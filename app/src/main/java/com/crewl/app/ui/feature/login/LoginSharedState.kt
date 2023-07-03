package com.crewl.app.ui.feature.login

import androidx.compose.ui.text.input.TextFieldValue
import com.crewl.app.data.model.country.Country
import com.crewl.app.data.model.user.PhoneNumber

data class LoginSharedState(
    val isLoading: Boolean = false,
    val number: PhoneNumber = PhoneNumber(Country(), ""),
    val phoneNumber: TextFieldValue = TextFieldValue(),
    val countryCode: String = "",
    val country: Country = Country(),
    val bottomSheetType: BottomSheetScreenType = BottomSheetScreenType.Empty,
    val query: String = "",
    val countries: List<Country> = emptyList()
)