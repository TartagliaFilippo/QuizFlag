package com.projects.quizflags.presentation.ui.components.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projects.quizflags.domain.model.Country
import com.projects.quizflags.domain.model.GameResult
import com.projects.quizflags.domain.model.QuizQuestion

@Composable
fun GameAnswersSection(
    question: QuizQuestion,
    selectedAnswer: String?,
    lastResult: GameResult?,
    showFeedback: Boolean,
    onAnswerSelected: (Country) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(question.options) { option ->
            GameAnswerButton(
                country = option,
                isSelected = selectedAnswer == option.code,
                isCorrect = when {
                    !showFeedback -> null
                    option.code == question.correctAnswer.code -> true
                    selectedAnswer == option.code && lastResult?.isCorrect == false -> false
                    else -> null
                },
                enabled = selectedAnswer == null,
                onClick = { onAnswerSelected(option) }
            )
        }
    }
}