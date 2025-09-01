package com.projects.quizflags.ui.responsive

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WindowWidthSizeClass { Compact, Medium, Expanded }
enum class WindowHeightSizeClass { Short, Medium, Tall }

data class WindowSize(
    val widthClass: WindowWidthSizeClass,
    val heightClass: WindowHeightSizeClass
)

sealed class DeviceType {
    object PhonePortrait : DeviceType()
    object PhoneLandscape : DeviceType()
    object TabletPortrait : DeviceType()
    object TabletLandscape : DeviceType()
    object Desktop : DeviceType()
}

fun calculateWindowSize(
    maxWidth: Dp,
    maxHeight: Dp
): WindowSize {
    val widthClass = when {
        maxWidth < 600.dp -> WindowWidthSizeClass.Compact
        maxWidth < 840.dp -> WindowWidthSizeClass.Medium
        else -> WindowWidthSizeClass.Expanded
    }

    val heightClass = when {
        maxHeight < 480.dp -> WindowHeightSizeClass.Short
        maxHeight < 900.dp -> WindowHeightSizeClass.Medium
        else -> WindowHeightSizeClass.Tall
    }

    return WindowSize(widthClass, heightClass)
}


fun toDeviceType(
    windowSize: WindowSize,
    maxWidth: Dp,
    maxHeight: Dp
): DeviceType {
    val isPortrait = maxHeight > maxWidth

    return when {
        // Telefono = se la dimensione minore (width o height) è < 600
        minOf(maxWidth, maxHeight) < 600.dp && isPortrait -> DeviceType.PhonePortrait
        minOf(maxWidth, maxHeight) < 600.dp && !isPortrait -> DeviceType.PhoneLandscape

        // Tablet = se la dimensione minore è >= 600
        minOf(maxWidth, maxHeight) in 600.dp..960.dp && isPortrait -> DeviceType.TabletPortrait
        minOf(maxWidth, maxHeight) in 600.dp..960.dp && !isPortrait -> DeviceType.TabletLandscape

        else -> DeviceType.Desktop
    }
}