package com.projects.quizflags.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.projects.quizflags.ui.responsive.responsiveTypography

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = PurpleSecondary,
    background = LightBackground,
    onPrimary = Color.White,
    onBackground = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkSecondary,
    background = DarkBackground,
    onPrimary = Color.Black,
    onBackground = Color.White
)

private val SpaceColorScheme = darkColorScheme(
    primary = SpacePrimary,
    secondary = SpaceSecondary,
    background = SpaceBackground,
    onPrimary = Color.Black,
    onBackground = Color(0xFFEAEAEA)
)

enum class AppTheme { LIGHT, DARK, SPACE }

@Composable
fun QuizFlagsTheme(
    windowSizeClass: WindowSizeClass,
    appTheme: AppTheme = AppTheme.DARK,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            when (appTheme) {
                AppTheme.LIGHT -> dynamicLightColorScheme(context)
                AppTheme.DARK -> dynamicDarkColorScheme(context)
                AppTheme.SPACE -> SpaceColorScheme // Dynamic non ha il tema space
            }
        }
        else -> when (appTheme) {
            AppTheme.LIGHT -> LightColorScheme
            AppTheme.DARK -> DarkColorScheme
            AppTheme.SPACE -> SpaceColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = responsiveTypography(windowSizeClass),
        content = content
    )
}