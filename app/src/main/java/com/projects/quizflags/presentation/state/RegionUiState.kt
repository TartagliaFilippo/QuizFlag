package com.projects.quizflags.presentation.state

import com.projects.quizflags.domain.model.Region

data class RegionUiState(
    val regions: List<Region> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)