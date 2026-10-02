package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = YtBrandOrange,
    onPrimary = Color.White,
    primaryContainer = YtBrandDarkSoft,
    onPrimaryContainer = Color(0xFFFFD8C2),
    secondary = YtSkyBlue,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF0369A1),
    onSecondaryContainer = Color.White,
    tertiary = YtEmerald,
    onTertiary = Color.White,
    background = DarkBg,
    onBackground = DarkText,
    surface = DarkSurface,
    onSurface = DarkText,
    surfaceVariant = DarkSurfaceMuted,
    onSurfaceVariant = DarkTextMuted,
    outline = DarkBorder,
    error = YtRose,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = YtBrandOrange,
    onPrimary = Color.White,
    primaryContainer = YtBrandSoft,
    onPrimaryContainer = Color(0xFF7A2300),
    secondary = YtSkyBlue,
    onSecondary = Color.White,
    secondaryContainer = YtSkySoft,
    onSecondaryContainer = Color(0xFF0369A1),
    tertiary = YtEmerald,
    onTertiary = Color.White,
    background = LightBg,
    onBackground = LightText,
    surface = LightSurface,
    onSurface = LightText,
    surfaceVariant = LightSurfaceMuted,
    onSurfaceVariant = LightTextMuted,
    outline = LightBorder,
    error = YtRose,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep YoungTube's vibrant brand colors by default
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
