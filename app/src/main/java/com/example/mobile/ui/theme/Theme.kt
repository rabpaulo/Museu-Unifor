package com.example.mobile.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import java.lang.reflect.Modifier

//Color Pallet
val DarkNavy = Color(0xFF0E153A)
val VividBlue = Color(0xFF3D5AF1)
val LightCyan = Color(0xFF22D1EE)
val LightAqua = Color(0xFFE2F3F5)

/*
Name	     Hex	     Description
Dark Navy	#0E153A	Background, Dark theme
Vivid Blue	#3D5AF1	Primary color
Light Cyan	#22D1EE	Accent or Secondary color
Light Aqua	#E2F3F5	Surface, Light Backgrounds
 */

private val DarkColorScheme = darkColorScheme(
    primary = VividBlue,
    onPrimary = Color.Black,
    primaryContainer = DarkNavy,
    onPrimaryContainer = LightAqua,
    secondary = LightCyan,
    onSecondary = LightAqua,
    background = DarkNavy,
    onBackground = LightAqua,
    surface = DarkNavy,
    onSurface = LightAqua
)

private val LightColorScheme = lightColorScheme(
    primary = VividBlue,
    onPrimary = Color.White,
    primaryContainer = LightAqua,
    onPrimaryContainer = DarkNavy,
    secondary = LightCyan,
    onSecondary = DarkNavy,
    background = LightAqua,
    onBackground = DarkNavy,
    surface = LightAqua,
    onSurface = DarkNavy
)

@Composable
fun MobileTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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
    ) {
        Surface(
          //  color = DarkNavy // Use the background color from the scheme
        ) {
            content()
        }
    }
}