package com.projects.quizflags.presentation.ui.screen.endgame

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.presentation.ui.layout.endgame.desktop.DesktopEndGameLayout
import com.projects.quizflags.presentation.ui.layout.endgame.phone.PhoneLandscapeEndGameLayout
import com.projects.quizflags.presentation.ui.layout.endgame.phone.PhonePortraitEndGameLayout
import com.projects.quizflags.presentation.ui.layout.endgame.tablet.TabletLandscapeEndGameLayout
import com.projects.quizflags.presentation.ui.layout.endgame.tablet.TabletPortraitEndGameLayout
import com.projects.quizflags.presentation.ui.responsive.DeviceType
import com.projects.quizflags.presentation.ui.responsive.calculateWindowSize
import com.projects.quizflags.presentation.ui.responsive.toDeviceType

@Composable
fun EndGameScreen(
    score: Int,
    totalQuestions: Int,
    gameMode: GameMode,
    onPlayAgain: () -> Unit,
    onNavigateHome: () -> Unit
) {
    EndGameContent(
        score = score,
        totalQuestions = totalQuestions,
        gameMode = gameMode,
        onPlayAgain = onPlayAgain,
        onNavigateHome = onNavigateHome
    )
}

@Composable
private fun EndGameContent(
    score: Int,
    totalQuestions: Int,
    gameMode: GameMode,
    onPlayAgain: () -> Unit,
    onNavigateHome: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        with(this) {
            val windowSize = calculateWindowSize(
                maxWidth = maxWidth,
                maxHeight = maxHeight
            )

            val deviceType = toDeviceType(
                windowSize,
                maxWidth,
                maxHeight
            )

            when (deviceType) {
                DeviceType.PhonePortrait -> PhonePortraitEndGameLayout(
                    score = score,
                    totalQuestions = totalQuestions,
                    gameMode = gameMode,
                    onPlayAgain = onPlayAgain,
                    onNavigateHome = onNavigateHome
                )
                DeviceType.PhoneLandscape -> PhoneLandscapeEndGameLayout(
                    score = score,
                    totalQuestions = totalQuestions,
                    gameMode = gameMode,
                    onPlayAgain = onPlayAgain,
                    onNavigateHome = onNavigateHome
                )
                DeviceType.TabletPortrait -> TabletPortraitEndGameLayout(
                    score = score,
                    totalQuestions = totalQuestions,
                    gameMode = gameMode,
                    onPlayAgain = onPlayAgain,
                    onNavigateHome = onNavigateHome
                )
                DeviceType.TabletLandscape -> TabletLandscapeEndGameLayout(
                    score = score,
                    totalQuestions = totalQuestions,
                    gameMode = gameMode,
                    onPlayAgain = onPlayAgain,
                    onNavigateHome = onNavigateHome
                )
                DeviceType.Desktop -> DesktopEndGameLayout(
                    score = score,
                    totalQuestions = totalQuestions,
                    gameMode = gameMode,
                    onPlayAgain = onPlayAgain,
                    onNavigateHome = onNavigateHome
                )
            }
        }
    }
}