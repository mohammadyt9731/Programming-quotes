package com.example.programmingquotes.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Navy,
    onPrimary = White700,
    background = White500,
    onBackground = SemiBlack,
    surface = LightGray,
    onSurface = Navy
)

private val LightColorPalette = lightColors(
    primary = Navy,
    onPrimary = White700,
    background = White500,
    onBackground = SemiBlack,
    surface = LightGray,
    onSurface = Navy
)

@Composable
internal fun ProgrammingQuotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}