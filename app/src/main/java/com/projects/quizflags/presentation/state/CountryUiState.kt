package com.projects.quizflags.presentation.state

import com.projects.quizflags.domain.model.Country
import com.projects.quizflags.domain.model.Region

data class CountryUiState(
    val countries: List<Country> = emptyList(),
    val selectedRegion: Region? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
