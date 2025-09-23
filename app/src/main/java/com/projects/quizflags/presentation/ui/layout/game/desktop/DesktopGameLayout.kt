package com.projects.quizflags.presentation.ui.layout.game.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projects.quizflags.domain.model.Country
import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.domain.model.GameResult
import com.projects.quizflags.domain.model.GameState
import com.projects.quizflags.domain.model.QuizQuestion
import com.projects.quizflags.presentation.ui.components.game.GameAnswersSection
import com.projects.quizflags.presentation.ui.components.game.GameProgressSection
import com.projects.quizflags.presentation.ui.components.game.GameQuestionSection

@Composable
fun DesktopGameLayout(
    question: QuizQuestion,
    gameState: GameState,
    selectedAnswer: String?,
    lastResult: GameResult?,
    showFeedback: Boolean,
    onAnswerSelected: (Country) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(48.dp)
    ) {
        // Sezione sinistra
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(end = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GameQuestionSection(
                question = question,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            GameProgressSection(
                score = gameState.score,
                round = gameState.round,
                totalQuestions = gameState.totalQuestions,
                gameMode = gameState.gameMode ?: GameMode.ClassicGame
            )
        }

        // Sezione centrale/destra
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(start = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            GameAnswersSection(
                question = question,
                selectedAnswer = selectedAnswer,
                lastResult = lastResult,
                showFeedback = showFeedback,
                onAnswerSelected = onAnswerSelected
            )
        }
    }
}