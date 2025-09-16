package com.projects.quizflags.presentation.ui.utility

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.projects.quizflags.domain.model.GameMode

fun getPerformanceEmoji(
    score: Int,
    totalQuestions: Int
): String {
    val percentage = if (totalQuestions > 0) (score * 100) / totalQuestions else 0
    return when {
        percentage == 100 -> "🏆"
        percentage >= 80 -> "🎉"
        percentage >= 60 -> "😊"
        percentage >= 40 -> "🙂"
        else -> "😔"
    }
}

fun getTrophyEmoji(percentage: Int): String {
    return when {
        percentage == 100 -> "🏆"
        percentage >= 90 -> "🥇"
        percentage >= 80 -> "🥈"
        percentage >= 70 -> "🥉"
        percentage >= 60 -> "⭐"
        else -> "💪"
    }
}

fun getTrophyTitle(percentage: Int): String {
    return when {
        percentage == 100 -> "Perfetto!"
        percentage >= 90 -> "Eccellente!"
        percentage >= 80 -> "Ottimo!"
        percentage >= 70 -> "Molto Bene!"
        percentage >= 60 -> "Buono!"
        else -> "Continua così!"
    }
}

@Composable
fun getScoreColor(
    score: Int,
    totalQuestions: Int
): Color {
    val percentage = if (totalQuestions > 0) (score * 100) / totalQuestions else 0
    return when {
        percentage >= 80 -> Color(0xFF4CAF50)
        percentage >= 60 -> Color(0xFFFF9800)
        else -> Color(0xFFF44336)
    }
}

fun getGameModeTitle(gameMode: GameMode): String {
    return when (gameMode) {
        is GameMode.ClassicGame -> "Gioco Classico Completato!"
        is GameMode.SurvivalGame -> "Modalità Sopravvivenza Terminata!"
        is GameMode.RegionGame -> "Quiz Regionale Completato!"
    }
}