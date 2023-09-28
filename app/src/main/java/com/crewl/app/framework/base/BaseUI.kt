package com.crewl.app.framework.base

import androidx.compose.runtime.Composable

interface BaseUI {
    @Composable
    fun create(): ComposableView
}