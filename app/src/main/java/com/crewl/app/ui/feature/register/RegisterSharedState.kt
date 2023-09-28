package com.crewl.app.ui.feature.register

import androidx.compose.ui.text.input.TextFieldValue
import com.crewl.app.data.model.country.Country
import com.crewl.app.data.model.user.PhoneNumber
import com.crewl.app.data.model.user.User
import com.crewl.app.ui.feature.login.BottomSheetScreenType

data class RegisterSharedState(
    val isLoading: Boolean = false,
    val number: PhoneNumber = PhoneNumber(Country(), ""),
    val name: TextFieldValue = TextFieldValue(),
    val surname: TextFieldValue = TextFieldValue(),
    val user: User = User(),
    val throwable: String = ""
)
