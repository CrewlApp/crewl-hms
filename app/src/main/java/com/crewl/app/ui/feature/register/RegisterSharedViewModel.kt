package com.crewl.app.ui.feature.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.crewl.app.data.model.user.PhoneNumber
import com.crewl.app.data.model.user.User
import com.crewl.app.framework.base.BaseEvent
import com.crewl.app.framework.base.BaseViewModel
import com.crewl.app.ui.feature.login.LoginSharedEvent
import com.google.type.LatLng
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterSharedViewModel @Inject constructor(
) : BaseViewModel()  {
    private val _registerUserInEventChannel = Channel<BaseEvent>()
    val registerUserInEventChannel: Flow<BaseEvent>
        get() = _registerUserInEventChannel.receiveAsFlow()

    private val _registerUserInStateFlow = mutableStateOf(RegisterSharedState())
    val registerUserInStateFlow: State<RegisterSharedState>
        get() = _registerUserInStateFlow

    private val _user = mutableStateOf(User())
    val user: State<User>
        get() = _user

    fun saveNameAndSurname(name: String, surname: String) {
        _user.value = User(name = name, surname = surname)
    }

    fun savePicture(pictureUrl: String) {
        _user.value = User(profilePicture = pictureUrl)
    }

    fun saveLocation(coordinates: LatLng) {
        _user.value = User(coordinates = coordinates)
    }

    fun savePhoneNumber(phoneNumber: PhoneNumber) {
        _user.value = User(phoneNumber = phoneNumber)
    }

    fun saveBirthdate(birthdate: String) {
        _user.value = User(birthdate = birthdate)
    }

    fun setLoading(isLoading: Boolean) {
        _registerUserInStateFlow.value = _registerUserInStateFlow.value.copy(isLoading = isLoading)
    }

    fun onSaveName() = viewModelScope.launch {
        _registerUserInStateFlow.value.apply {
            _registerUserInStateFlow.value = this.copy(name = name)

            _registerUserInEventChannel.send(RegisterSharedEvent.SaveName(name.text))
        }
    }

    fun onSaveSurname() = viewModelScope.launch {
        _registerUserInStateFlow.value.apply {
            _registerUserInStateFlow.value = this.copy(surname = surname)

            _registerUserInEventChannel.send(RegisterSharedEvent.SaveName(surname.text))
        }
    }

    fun updateName(name: TextFieldValue) = viewModelScope.launch {
        _registerUserInStateFlow.value.apply {
            _registerUserInStateFlow.value = copy(
                name = name
            )
        }
    }

    fun updateSurname(surname: TextFieldValue) = viewModelScope.launch {
        _registerUserInStateFlow.value.apply {
            _registerUserInStateFlow.value = copy(
                surname = surname
            )
        }
    }

    fun onNavigate(route: String) = viewModelScope.launch {
        _registerUserInEventChannel.send(BaseEvent.Navigate(route = route))
    }
}