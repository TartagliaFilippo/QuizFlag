package com.projects.quizflags.domain.model

import androidx.annotation.StringRes

data class GamesRoute(
    val id: Int,
    @StringRes val name: Int,
    val mode: GameMode?,
    val icon: Int
)
