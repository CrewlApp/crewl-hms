package com.crewl.app.framework.base

interface BaseEvent {
    data class InternetConnection(val isActive: Boolean) : BaseEvent
    data class Error(val message: String) : BaseEvent
    data class Navigate(val route: String) : BaseEvent
    object Loading : BaseEvent
}