package com.crewl.app.ui.feature.login

import androidx.compose.ui.text.input.TextFieldValue

sealed class LoginEvent {
    data class PhoneNumberChanged(val phoneNumber: TextFieldValue) : LoginEvent()
    data class SavePhoneNumber(val phoneNumber: TextFieldValue) : LoginEvent()
    data class SavedPhoneNumberSuccess(val status: Boolean) : LoginEvent()
    object Navigate : LoginEvent()
}