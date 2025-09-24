package com.projects.quizflags.presentation.ui.screen.game

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.quizflags.domain.model.Country
import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.domain.model.GameResult
import com.projects.quizflags.domain.model.GameState
import com.projects.quizflags.domain.model.QuizQuestion
import com.projects.quizflags.presentation.state.GameUiEvent
import com.projects.quizflags.presentation.state.GameUiState
import com.projects.quizflags.presentation.ui.components.common.EmptyContent
import com.projects.quizflags.presentation.ui.components.common.ErrorContent
import com.projects.quizflags.presentation.ui.components.common.LoadingContent
import com.projects.quizflags.presentation.ui.components.game.GameTopBar
import com.projects.quizflags.presentation.ui.layout.game.desktop.DesktopGameLayout
import com.projects.quizflags.presentation.ui.layout.game.phone.PhoneLandscapeGameLayout
import com.projects.quizflags.presentation.ui.layout.game.phone.PhonePortraitGameLayout
import com.projects.quizflags.presentation.ui.layout.game.tablet.TabletLandscapeGameLayout
import com.projects.quizflags.presentation.ui.layout.game.tablet.TabletPortraitGameLayout

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
        EmptyContent()
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
        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        when{
            maxWidth < 600.dp -> {
                PhonePortraitGameLayout(
                    question = question,
                    gameState = gameState,
                    selectedAnswer = selectedAnswer,
                    lastResult = lastResult,
                    showFeedback = showFeedback,
                    onAnswerSelected = onAnswerSelected
                )
            }

            maxWidth < 600.dp && isLandscape -> {
                PhoneLandscapeGameLayout(
                    question = question,
                    gameState = gameState,
                    selectedAnswer = selectedAnswer,
                    lastResult = lastResult,
                    showFeedback = showFeedback,
                    onAnswerSelected = onAnswerSelected
                )
            }

            maxWidth < 840.dp && !isLandscape -> {
                TabletPortraitGameLayout(
                    question = question,
                    gameState = gameState,
                    selectedAnswer = selectedAnswer,
                    lastResult = lastResult,
                    showFeedback = showFeedback,
                    onAnswerSelected = onAnswerSelected
                )
            }

            maxWidth < 840.dp && isLandscape -> {
                TabletLandscapeGameLayout(
                    question = question,
                    gameState = gameState,
                    selectedAnswer = selectedAnswer,
                    lastResult = lastResult,
                    showFeedback = showFeedback,
                    onAnswerSelected = onAnswerSelected
                )
            }

            else -> {
                DesktopGameLayout(
                    question = question,
                    gameState = gameState,
                    selectedAnswer = selectedAnswer,
                    lastResult = lastResult,
                    showFeedback = showFeedback,
                    onAnswerSelected = onAnswerSelected
                )
            }
        }
    }
}