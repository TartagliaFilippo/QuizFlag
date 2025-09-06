package com.projects.quizflags.ui.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.projects.quizflags.R
import com.projects.quizflags.domain.model.Country
import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.domain.model.QuizQuestion
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries: StateFlow<List<Country>> = _countries

    private val _currentQuestion = MutableStateFlow<QuizQuestion?>(null)
    val currentQuestion: StateFlow<QuizQuestion?> = _currentQuestion

    // Turni di gioco
    private val _round = MutableStateFlow(0)
    val round: StateFlow<Int> = _round

    // Punteggio
    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _isGameOver = MutableStateFlow(false)
    val isGameOver: StateFlow<Boolean> = _isGameOver

    private var availableCountries: MutableList<Country> = mutableListOf()
    private lateinit var gameMode: GameMode

    init {
        loadCountries()
    }

    private fun loadCountries() {
        val inputStream = context.resources.openRawResource(R.raw.countries)
        val json = inputStream.bufferedReader().use { it.readText() }

        val parsed = Json {
            ignoreUnknownKeys = true
        }.decodeFromString<List<Country>>(json)

        _countries.value = parsed
    }

    fun startGame(mode: GameMode) {
        gameMode = mode
        _round.value = 0
        _score.value = 0

        availableCountries = when (mode) {
            is GameMode.ClassicGame -> countries.value.shuffled().take(10).toMutableList()
            is GameMode.RegionGame -> countries.value.filter { it.region == mode.regionCode }.toMutableList()
            is GameMode.SurvivalGame -> countries.value.toMutableList()
        }

        Log.d("GAME", "Modalità: $mode - Paesi trovati: ${availableCountries.size}")

        generateNewQuestion()
    }

    fun generateNewQuestion() {
        // Gestione dei 10 turni di gioco
        if (availableCountries.isEmpty()) {
            _isGameOver.value = true
            _currentQuestion.value = null
            Log.e("END GAME", "Finito le nazioni")
            return
        }

        // Seleziono la risposta corretta
        val correct = availableCountries.random()
        Log.d("Risposta Corretta" ,correct.toString())
        availableCountries.remove(correct)

        // Seleziono le risposte sbagliate
        val wrongs = _countries.value
            .filter { it.code != correct.code }
            .shuffled()
            .take(3)

        // Creo le varie opzioni del quiz
        val options = (wrongs + correct).shuffled()

        // Creo l'oggetto di gioco
        _currentQuestion.value = QuizQuestion(
            correctAnswer = correct,
            options = options
        )

        // Gestisco il turno di gioco
        _round.value += 1

        // Gestisco il fine partita
        when (gameMode) {
            is GameMode.ClassicGame -> if (_round.value > 10) _isGameOver.value = true
            is GameMode.RegionGame -> if (availableCountries.isEmpty()) _isGameOver.value = true
            is GameMode.SurvivalGame -> { /* già gestito in submitAnswer */ }
        }
    }

    fun submitAnswer(selected: Country) {
        val current = _currentQuestion.value ?: return
        val isCorrect = selected.code == current.correctAnswer.code

        if (isCorrect) {
            _score.value += 1
        }

        when (gameMode) {
            is GameMode.SurvivalGame -> {
                if (!isCorrect) {
                    _isGameOver.value = true
                } else {
                    generateNewQuestion()
                }
            }
            else -> generateNewQuestion()
        }
    }
}