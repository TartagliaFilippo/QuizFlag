package com.projects.quizflags.presentation.ui.components.common

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.projects.quizflags.presentation.ui.responsive.DeviceType
import com.projects.quizflags.presentation.ui.responsive.calculateWindowSize
import com.projects.quizflags.presentation.ui.responsive.toDeviceType

@Composable
fun ResponsiveContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxWithConstraintsScope.(DeviceType) -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        val windowSize = calculateWindowSize(
            maxWidth = maxWidth,
            maxHeight = maxHeight
        )

        val deviceType = toDeviceType(
            windowSize,
            maxWidth,
            maxHeight
        )

        content(deviceType)
    }
}