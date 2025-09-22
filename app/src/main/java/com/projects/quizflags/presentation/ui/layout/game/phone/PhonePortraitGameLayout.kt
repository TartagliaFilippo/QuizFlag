package com.projects.quizflags.presentation.ui.layout.game.phone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
fun PhonePortraitGameLayout(
    question: QuizQuestion,
    gameState: GameState,
    selectedAnswer: String?,
    lastResult: GameResult?,
    showFeedback: Boolean,
    onAnswerSelected: (Country) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(56.dp))

        GameQuestionSection(
            question = question,
            modifier = Modifier.weight(1f)
        )

        GameAnswersSection(
            question = question,
            selectedAnswer = selectedAnswer,
            lastResult = lastResult,
            showFeedback = showFeedback,
            onAnswerSelected = onAnswerSelected,
            modifier = Modifier.weight(1f)
        )

        GameProgressSection(
            score = gameState.score,
            round = gameState.round,
            totalQuestions = gameState.totalQuestions,
            gameMode = gameState.gameMode ?: GameMode.ClassicGame,
            modifier = Modifier.weight(0.3f)
        )
    }
}