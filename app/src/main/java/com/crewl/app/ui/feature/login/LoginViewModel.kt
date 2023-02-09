package com.crewl.app.ui.feature.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.crewl.app.framework.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor() : BaseViewModel() {
    private val _logUserInEventChannel = Channel<LoginEvent>()
    val logUserInEvent = _logUserInEventChannel.receiveAsFlow()

    private val _phoneNumber = mutableStateOf(TextFieldValue(""))
    val phoneNumber: State<TextFieldValue>
        get() = _phoneNumber

    fun savePhoneNumber(phoneNumber: TextFieldValue) {
        if (validatePhoneNumber(phoneNumber.text)) {
            onSavePhoneNumberSuccess(status = true)
            onNavigate()

            return
        }

        onSavePhoneNumberSuccess(status = false)
    }

    fun updatePhoneNumber(phoneNumber: TextFieldValue) {
        viewModelScope.launch(Dispatchers.IO) {
            _phoneNumber.value = phoneNumber
        }
    }

    fun onPhoneNumberChanged(phoneNumber: TextFieldValue) = viewModelScope.launch {
        _logUserInEventChannel.send(LoginEvent.PhoneNumberChanged(phoneNumber = phoneNumber))
    }

    private fun onSavePhoneNumberSuccess(status: Boolean) = viewModelScope.launch {
        _logUserInEventChannel.send(LoginEvent.SavedPhoneNumberSuccess(status = status))
    }

    fun onSavePhoneNumber(phoneNumber: TextFieldValue) = viewModelScope.launch {
        _logUserInEventChannel.send(LoginEvent.SavePhoneNumber(phoneNumber = phoneNumber))
    }

    private fun onNavigate() = viewModelScope.launch {
        _logUserInEventChannel.send(LoginEvent.Navigate)
    }
}

fun validatePhoneNumber(phoneNumber: String): Boolean = phoneNumber.isNotEmpty() && android.util.Patterns.PHONE.matcher(phoneNumber).matches()