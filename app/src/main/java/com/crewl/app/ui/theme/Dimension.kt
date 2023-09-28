package com.crewl.app.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalDim = compositionLocalOf { PaddingValues() }

@Composable
internal fun singlePadding() = 8.dp

@Composable
internal fun iconSize() = 48.dp

data class PaddingValues(
    val small: Dp = 10.dp,
    val medium: Dp = 20.dp,
    val large: Dp = 30.dp
)