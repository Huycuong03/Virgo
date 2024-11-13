package com.example.virgo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColorScheme = lightColorScheme(
    // Primary, Secondary, Tertiary Colors
    primary = ColorGradient1 ,  // Deep Green (for primary action or main branding)
    secondary = ColorGradient2,  // Dark Blue (for secondary action or accents)
    tertiary = ColorAccent,  // Teal Green (for medical-oriented accents)

    // Background & Surface Colors
    background = ColorBackground,  // Light background with a slight teal tint
    surface = ColorSurface,  // Clean white surface for cards, buttons, etc.

    // On Colors (for text/icons on primary, secondary, etc.)
    onPrimary = Color.White,  // Text/icons on primary background will be white for contrast
    onSecondary = Color.White,  // Text/icons on secondary background will be white for contrast
    onTertiary = Color.White,  // Text/icons on tertiary background will be white for contrast

    // Text and Icon Colors
    onBackground = ColorTextPrimary,  // Dark primary text on background
    onSurface = ColorTextPrimary,  // Dark primary text on surfaces (cards, etc.)

    // Additional Text Variants
    onError = Color.White,  // White text/icons on error states
)


@Composable
fun VirgoTheme(
    content: @Composable () -> Unit
) {
        MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}