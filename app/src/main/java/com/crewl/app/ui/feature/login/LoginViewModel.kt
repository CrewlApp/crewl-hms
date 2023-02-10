package com.crewl.app.ui.feature.login

import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.crewl.app.framework.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class BottomSheetType {
    Empty, PrivacyPolicy, TermsOfService
}

class LoginViewModel @Inject constructor() : BaseViewModel() {
    private val _logUserInEventChannel = Channel<LoginEvent>()
    val logUserInEvent: Flow<LoginEvent>
        get() = _logUserInEventChannel.receiveAsFlow()

    private val _phoneNumber = mutableStateOf(TextFieldValue(""))
    val phoneNumber: State<TextFieldValue>
        get() = _phoneNumber

    private var _bottomSheetType = mutableStateOf(BottomSheetType.Empty)
    val bottomSheetType: State<BottomSheetType>
        get() = _bottomSheetType

    fun savePhoneNumber(phoneNumber: TextFieldValue) {
        if (validatePhoneNumber(phoneNumber.text)) {
            onSavePhoneNumberSuccess(status = true)
            onNavigate()

            return
        }

        onSavePhoneNumberSuccess(status = false)
    }

    /**
     * @param phoneNumber as TextFieldValue
     *
     * Change _phoneNumber value with given parameter.
     */
    fun updatePhoneNumber(phoneNumber: TextFieldValue) {
        viewModelScope.launch(Dispatchers.IO) {
            _phoneNumber.value = phoneNumber
        }
    }

    /**
     * @param type as BottomSheetType
     *
     * Change _bottomSheetType value with given parameter.
     */
    private fun changeBottomSheetType(type: BottomSheetType) {
        viewModelScope.launch {
            _bottomSheetType.value = type
        }
    }

    // region UI events
    /**
     * @param phoneNumber as TextFieldValue
     * @see LoginEvent.PhoneNumberChanged
     */
    fun onPhoneNumberChanged(phoneNumber: TextFieldValue) = viewModelScope.launch {
        _logUserInEventChannel.send(LoginEvent.PhoneNumberChanged(phoneNumber = phoneNumber))
    }

    /**
     * @param status as Boolean
     * @see LoginEvent.SavedPhoneNumberSuccess
     */
    private fun onSavePhoneNumberSuccess(status: Boolean) = viewModelScope.launch {
        _logUserInEventChannel.send(LoginEvent.SavedPhoneNumberSuccess(status = status))
    }

    /**
     * @param phoneNumber as TextFieldValue
     * @see LoginEvent.SavePhoneNumber
     */
    fun onSavePhoneNumber(phoneNumber: TextFieldValue) = viewModelScope.launch {
        _logUserInEventChannel.send(LoginEvent.SavePhoneNumber(phoneNumber = phoneNumber))
    }

    /**
     * @see LoginEvent.Navigate
     */
    private fun onNavigate() = viewModelScope.launch {
        _logUserInEventChannel.send(LoginEvent.Navigate)
    }

    /**
     * @param type as BottomSheetType
     * @see LoginEvent.OpenBottomSheet
     */
    fun onBottomSheetClicked(type: BottomSheetType) = viewModelScope.launch {
        changeBottomSheetType(type = type)
        _logUserInEventChannel.send(LoginEvent.OpenBottomSheet(type = type))
    }
    // endregion
}

fun validatePhoneNumber(phoneNumber: String): Boolean = phoneNumber.isNotEmpty() && android.util.Patterns.PHONE.matcher(phoneNumber).matches()