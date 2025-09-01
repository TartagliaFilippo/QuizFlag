package com.projects.quizflags.ui.viewModel

import androidx.lifecycle.ViewModel
import com.projects.quizflags.R
import com.projects.quizflags.data.model.GameMode
import com.projects.quizflags.data.model.GamesRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
open class HomeViewModel @Inject constructor() : ViewModel() {
    private val _games = MutableStateFlow(
        listOf(
            GamesRoute(
                id = 1,
                name = R.string.game_classic,
                mode = GameMode.ClassicGame,
                icon = R.drawable.home_startflag
            ),
            GamesRoute(
                id = 2,
                name = R.string.game_region,
                mode = null,
                icon = R.drawable.home_world
            ),
            GamesRoute(
                id = 3,
                name = R.string.game_survival,
                mode = GameMode.SurvivalGame,
                icon = R.drawable.home_survival
            )
        )
    )
    open val games: StateFlow<List<GamesRoute>> = _games.asStateFlow()
}