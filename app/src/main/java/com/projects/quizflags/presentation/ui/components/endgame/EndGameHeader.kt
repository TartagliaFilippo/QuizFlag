package com.projects.quizflags.presentation.ui.components.endgame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.presentation.ui.utility.getGameModeTitle
import com.projects.quizflags.presentation.ui.utility.getPerformanceEmoji
import com.projects.quizflags.presentation.ui.utility.getScoreColor

@Composable
fun EndGameHeader(
    score: Int,
    totalQuestions: Int,
    gameMode: GameMode,
    isCompact: Boolean
) {
    val headerStyle = if (isCompact) {
        MaterialTheme.typography.headlineMedium
    } else {
        MaterialTheme.typography.headlineLarge
    }

    val scoreStyle = if (isCompact) {
        MaterialTheme.typography.titleLarge
    } else {
        MaterialTheme.typography.headlineMedium
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = getPerformanceEmoji(score, totalQuestions),
            fontSize = if (isCompact) 48.sp else 72.sp
        )

        Spacer(modifier = Modifier.height(if (isCompact) 8.dp else 16.dp))

        Text(
            text = getGameModeTitle(gameMode),
            style = headerStyle,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(if (isCompact) 8.dp else 16.dp))

        Text(
            text = "Punteggio: $score/$totalQuestions",
            style = scoreStyle,
            color = getScoreColor(score, totalQuestions)
        )
    }
}