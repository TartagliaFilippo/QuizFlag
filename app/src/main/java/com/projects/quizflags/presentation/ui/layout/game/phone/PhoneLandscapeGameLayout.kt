package com.projects.quizflags.presentation.ui.layout.game.phone

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
fun PhoneLandscapeGameLayout(
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
            .padding(16.dp)
    ) {
        // Sezione sinistra
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(28.dp))

            GameQuestionSection(
                question = question,
                modifier = Modifier.weight(1f)
            )

            GameProgressSection(
                score = gameState.score,
                round = gameState.round,
                totalQuestions = gameState.totalQuestions,
                gameMode = gameState.gameMode ?: GameMode.ClassicGame,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        // Sezione destra
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center
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