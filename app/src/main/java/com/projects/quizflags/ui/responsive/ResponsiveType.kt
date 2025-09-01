package com.projects.quizflags.ui.responsive

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.Typography
import com.projects.quizflags.ui.theme.baseTypography

fun responsiveTypography(windowSizeClass: WindowSizeClass): Typography {
    return when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> baseTypography(scale = 1f)   // telefono
        WindowWidthSizeClass.Medium -> baseTypography(scale = 1.15f) // tablet piccolo
        WindowWidthSizeClass.Expanded -> baseTypography(scale = 1.3f) // tablet grande / desktop
        else -> baseTypography(scale = 1f)
    }
}