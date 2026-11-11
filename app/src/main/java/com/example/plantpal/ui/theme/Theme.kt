package com.example.plantpal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = LeafGreen,
    onPrimary = Color.White,
    primaryContainer = LeafGreenContainer,
    onPrimaryContainer = OnLeafGreenContainer,
    secondary = WaterBlue,
    onSecondary = Color.White,
    secondaryContainer = WaterBlueContainer,
    onSecondaryContainer = OnWaterBlueContainer,
    background = PaperBackground,
    onBackground = InkText,
    surface = PaperBackground,
    onSurface = InkText,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = OutlineLight,
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF0F5F1),
    surfaceContainer = Color(0xFFEAF1EC),
    surfaceContainerHigh = Color(0xFFE4ECE6),
    surfaceContainerHighest = Color(0xFFDDE8E0)
)

private val DarkColors = darkColorScheme(
    primary = LeafGreenDark,
    onPrimary = OnLeafGreenDark,
    primaryContainer = LeafGreenContainerDark,
    onPrimaryContainer = OnLeafGreenContainerDark,
    secondary = WaterBlueDark,
    onSecondary = OnWaterBlueDark,
    secondaryContainer = WaterBlueContainerDark,
    onSecondaryContainer = OnWaterBlueContainerDark,
    background = NightBackground,
    onBackground = NightText,
    surface = NightBackground,
    onSurface = NightText,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark,
    surfaceContainerLowest = Color(0xFF0B120E),
    surfaceContainerLow = Color(0xFF16211B),
    surfaceContainer = Color(0xFF1A2620),
    surfaceContainerHigh = Color(0xFF25322B),
    surfaceContainerHighest = Color(0xFF303D35)
)

@Composable
fun PlantPalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}