package com.projects.quizflags.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.quizflags.domain.model.Country
import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.domain.model.QuizQuestion
import com.projects.quizflags.domain.repository.GameRepository
import com.projects.quizflags.domain.usecase.GetNextQuestionUseCase
import com.projects.quizflags.domain.usecase.StartGameUseCase
import com.projects.quizflags.domain.usecase.SubmitAnswerUseCase
import com.projects.quizflags.presentation.state.GameUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val startGameUseCase: StartGameUseCase,
    private val submitAnswerUseCase: SubmitAnswerUseCase,
    private val getNextQuestionUseCase: GetNextQuestionUseCase,
    private val gameRepository: GameRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    val currentQuestion: StateFlow<QuizQuestion?> = _uiState
        .map { it.gameState.currentQuestion }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            null
        )

    val score: StateFlow<Int> = _uiState
        .map { it.gameState.score }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            0
        )

    val round: StateFlow<Int> = _uiState
        .map { it.gameState.round }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            0
        )

    val isGameOver: StateFlow<Boolean> = _uiState
        .map { it.gameState.isGameOver }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            false
        )

    fun startGame(mode: GameMode) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            startGameUseCase(mode).fold(
                onSuccess = { question ->
                    val gameState = gameRepository.getCurrentGameState()
                    _uiState.value = GameUiState(
                        gameState = gameState,
                        isLoading = false
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message
                    )
                }
            )
        }
    }

    fun submitAnswer(selectedCountry: Country) {
        viewModelScope.launch {
            submitAnswerUseCase(selectedCountry).fold(
                onSuccess = { result ->
                    _uiState.value = _uiState.value.copy(lastResult = result)

                    // Delay per feedback
                    delay(1500)

                    if (!result.isCorrect && _uiState.value.gameState.gameMode is GameMode.SurvivalGame) {
                        val gameState = gameRepository.getCurrentGameState()
                        _uiState.value = _uiState.value.copy(gameState = gameState)
                    } else {
                        getNextQuestion()
                    }
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(error = exception.message)
                }
            )
        }
    }

    private suspend fun getNextQuestion() {
        getNextQuestionUseCase().fold(
            onSuccess = { question ->
                val gameState = gameRepository.getCurrentGameState()
                _uiState.value = _uiState.value.copy(
                    gameState = gameState,
                    lastResult = null
                )
            },
            onFailure = { exception ->
                _uiState.value = _uiState.value.copy(error = exception.message)
            }
        )
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}