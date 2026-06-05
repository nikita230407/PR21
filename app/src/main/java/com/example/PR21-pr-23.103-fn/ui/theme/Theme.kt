package com.example.pz21last.ui.theme

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
    primary = MedicalBlue80,
    secondary = Mint80,
    tertiary = Coral80,
    background = Color(0xFF111820),
    surface = Color(0xFF18232D)
)

private val LightColorScheme = lightColorScheme(
    primary = MedicalBlue40,
    secondary = Mint40,
    tertiary = Coral40,
    background = Color(0xFFF5F8FB),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFE5EEF5),
    onSurfaceVariant = Color(0xFF475866)
)

@Composable
fun PZ21LastTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
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
