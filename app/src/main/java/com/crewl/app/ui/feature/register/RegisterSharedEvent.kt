package com.crewl.app.ui.feature.register

import androidx.compose.ui.text.input.TextFieldValue
import com.crewl.app.framework.base.BaseEvent

sealed class RegisterSharedEvent : BaseEvent {
    data class NameChanged(val name: TextFieldValue) : RegisterSharedEvent()
    data class SurnameChanged(val surname: TextFieldValue) : RegisterSharedEvent()
    data class SaveName(val name: String) : RegisterSharedEvent()
    data class SaveSurname(val surname: String) : RegisterSharedEvent()
    data class SavedNameSuccess(val status: Boolean) : RegisterSharedEvent()
    data class SavedSurnameSuccess(val status: Boolean) : RegisterSharedEvent()
}