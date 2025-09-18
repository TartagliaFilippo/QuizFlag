package com.projects.quizflags.presentation.ui.screen.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.quizflags.domain.model.Country
import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.domain.model.GameResult
import com.projects.quizflags.domain.model.GameState
import com.projects.quizflags.domain.model.QuizQuestion
import com.projects.quizflags.presentation.state.GameUiEvent
import com.projects.quizflags.presentation.state.GameUiState
import com.projects.quizflags.presentation.ui.components.common.ErrorContent
import com.projects.quizflags.presentation.ui.components.common.LoadingContent
import com.projects.quizflags.presentation.ui.components.game.GameAnswersSection
import com.projects.quizflags.presentation.ui.components.game.GameFlagImage
import com.projects.quizflags.presentation.ui.components.game.GameProgressSection
import com.projects.quizflags.presentation.ui.components.game.GameTopBar

@Composable
fun GameScreen(
    gameMode: GameMode,
    onNavigateToEndGame: (Int, Int, GameMode) -> Unit,
    onNavigateBack: () -> Unit,
    gameViewModel: GameViewModel = hiltViewModel()
) {
    val uiState by gameViewModel.uiState.collectAsState()

    LaunchedEffect(gameMode) {
        gameViewModel.startGame(gameMode)
    }

    LaunchedEffect(uiState.gameState.isGameOver) {
        if (uiState.gameState.isGameOver) {
            onNavigateToEndGame(
                uiState.gameState.score,
                uiState.gameState.totalQuestions,
                gameMode
            )
        }
    }

    val onEvent: (GameUiEvent) -> Unit = { event ->
        when (event) {
            is GameUiEvent.AnswerSelected -> gameViewModel.submitAnswer(event.country)
            GameUiEvent.ClearError -> gameViewModel.clearError()
            GameUiEvent.RetryGame -> gameViewModel.startGame(gameMode)
            else -> { /* TODO: Implementare gli altri eventi */ }
        }
    }

    GameScreenContent(
        uiState = uiState,
        onEvent = onEvent,
        onNavigateBack = onNavigateBack
    )
}

@Composable
private fun GameScreenContent(
    uiState: GameUiState,
    onEvent: (GameUiEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                LoadingContent()
            }
            uiState.error != null -> {
                ErrorContent(
                    error = uiState.error,
                    onRetry = { onEvent(GameUiEvent.RetryGame) },
                    onDismiss = { onEvent(GameUiEvent.ClearError) }
                )
            }
            else -> {
                GamePlayContent(
                    uiState = uiState,
                    onEvent = onEvent
                )
            }
        }

        GameTopBar(
            modifier = Modifier.align(Alignment.TopCenter),
            onNavigateBack = onNavigateBack
        )
    }
}

@Composable
private fun GamePlayContent(
    uiState: GameUiState,
    onEvent: (GameUiEvent) -> Unit
) {
    val question = uiState.gameState.currentQuestion

    if (question == null) {
        GameEmptyState()
        return
    }

    ResponsiveGameLayout(
        question = question,
        gameState = uiState.gameState,
        selectedAnswer = uiState.selectedAnswer,
        lastResult = uiState.lastResult,
        showFeedback = uiState.showAnswerFeedback,
        onAnswerSelected = { country ->
            onEvent(GameUiEvent.AnswerSelected(country))
        }
    )
}

@Composable
private fun ResponsiveGameLayout(
    question: QuizQuestion,
    gameState: GameState,
    selectedAnswer: String?,
    lastResult: GameResult?,
    showFeedback: Boolean,
    onAnswerSelected: (Country) -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        when{
            maxWidth < 600.dp -> {
                MobileGameLayout(
                    question = question,
                    gameState = gameState,
                    selectedAnswer = selectedAnswer,
                    lastResult = lastResult,
                    showFeedback = showFeedback,
                    onAnswerSelected = onAnswerSelected
                )
            }
            maxWidth < 840.dp -> {
                // TODO: Implementa la vista tablet
            }
            else -> {
                // TODO: Implementa la vista desktop
            }
        }
    }
}

@Composable
private fun MobileGameLayout(
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
        // Spacer per TopBar
        Spacer(modifier = Modifier.height(56.dp))

        // Flag e domanda
        GameQuestionSection(
            question = question,
            modifier = Modifier.weight(1f)
        )

        // Bottoni risposte
        GameAnswersSection(
            question = question,
            selectedAnswer = selectedAnswer,
            lastResult = lastResult,
            showFeedback = showFeedback,
            onAnswerSelected = onAnswerSelected,
            modifier = Modifier.weight(1f)
        )

        // Score e progresso
        GameProgressSection(
            score = gameState.score,
            round = gameState.round,
            totalQuestions = gameState.totalQuestions,
            gameMode = gameState.gameMode ?: GameMode.ClassicGame,
            modifier = Modifier.weight(0.3f)
        )
    }
}

@Composable
private fun GameQuestionSection(
    question: QuizQuestion,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Flag Image
        GameFlagImage(
            flagResourceId = question.flagResourceId,
            country = question.correctAnswer,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 2f)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Question Text
        Text(
            text = "Qual è questo paese?",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun GameEmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Nessuna domanda disponibile",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}