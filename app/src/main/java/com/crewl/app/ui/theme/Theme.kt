package com.crewl.app.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = SoftPeach,
    primaryVariant = SoftPeach,
    onPrimary = White,
    secondary = Black,
    secondaryVariant = SoftPeach,
    onSecondary = Black,

    background = SoftPeach,
    onBackground = SoftPeach,

    surface = SoftPeach,
    onSurface = SoftPeach
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = SoftPeach,
    primaryVariant = SoftPeach,
    onPrimary = Black,
    secondary = SoftPeach,
    secondaryVariant = SoftPeach,
    onSecondary = Black,

    background = SoftPeach,
    onBackground = SoftPeach,

    surface = Black,
    onSurface = White
)

val CrewlColors: Colors
    @Composable get() = MaterialTheme.colors

val CrewlShapes: Shapes
    @Composable get() = MaterialTheme.shapes

val CrewlTypography: Typography
    @Composable get() = MaterialTheme.typography

@Composable
fun CrewlTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    val typography = if (darkTheme) DarkTypography else LightTypography

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}