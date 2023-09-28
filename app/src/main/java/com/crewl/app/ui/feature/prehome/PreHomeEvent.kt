package com.crewl.app.ui.feature.prehome

sealed class PreHomeEvent {
    object NavigateLogin : PreHomeEvent()
    object NavigateRegister : PreHomeEvent()
}
