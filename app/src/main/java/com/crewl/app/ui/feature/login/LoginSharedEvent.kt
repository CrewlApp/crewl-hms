package com.crewl.app.ui.feature.login

import androidx.compose.ui.text.input.TextFieldValue
import com.crewl.app.data.model.user.PhoneNumber

sealed class LoginSharedEvent {
    data class PhoneNumberChanged(val number: TextFieldValue) : LoginSharedEvent()
    data class SavePhoneNumber(val number: PhoneNumber) : LoginSharedEvent()
    data class SavedPhoneNumberSuccess(val status: Boolean) : LoginSharedEvent()
    data class ToggleBottomSheet(val type: BottomSheetScreenType) : LoginSharedEvent()
    data class CodeChanged(val code: TextFieldValue) : LoginSharedEvent()
    data class Navigate(val route: String) : LoginSharedEvent()
    object Loading : LoginSharedEvent()
}

class FirebaseAuthNotInitialized : RuntimeException("FirebaseAuthNotInitialized")
class UserIsNullError : RuntimeException("UserIsNullError")
class TokenIdIsNullError : RuntimeException("TokenIdIsNullError")
class InvalidVerificationCodeError : RuntimeException("InvalidVerificationCodeError")
class UnexpectedError : RuntimeException("UnexpectedError")