package com.projects.quizflags.domain.model

sealed class GameMode(val route: String) {
    object ClassicGame : GameMode("classic_game")
    data class RegionGame(val regionCode: String) : GameMode("region_game")
    object SurvivalGame : GameMode("survival_game")
}