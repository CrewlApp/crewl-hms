package com.crewl.app.ui.feature.prehome

import androidx.lifecycle.viewModelScope
import com.crewl.app.framework.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreHomeViewModel @Inject constructor() : BaseViewModel() {
    private val _preHomeEventChannel: Channel<PreHomeEvent> = Channel()
    val preHomeEvent = _preHomeEventChannel.receiveAsFlow()

    fun onLoginClicked() = viewModelScope.launch(Dispatchers.IO) {
        _preHomeEventChannel.send(PreHomeEvent.NavigateLogin)
    }

    fun onRegisterClicked() = viewModelScope.launch(Dispatchers.IO) {
        _preHomeEventChannel.send(PreHomeEvent.NavigateRegister)
    }
}