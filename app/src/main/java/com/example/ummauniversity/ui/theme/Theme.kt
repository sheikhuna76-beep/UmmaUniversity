package com.example.ummauniversity.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = UmmaGreenPrimary,
    onPrimary = Color.White,
    primaryContainer = UmmaGreenSecondary,
    onPrimaryContainer = Color.White,
    secondary = UmmaGoldAccent,
    onSecondary = Color.Black,
    secondaryContainer = UmmaGoldLight,
    onSecondaryContainer = Color(0xFF3B2F00),
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = Color(0xFF424940),
    outline = OutlineLight
)

@Composable
fun UmmaUniversityTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
