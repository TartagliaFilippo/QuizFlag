package com.projects.quizflags.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.quizflags.domain.usecase.GetRegionUseCase
import com.projects.quizflags.presentation.state.RegionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class RegionViewModel @Inject constructor(
    private val getRegionUseCase: GetRegionUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegionUiState(isLoading = true))
    val uiState: StateFlow<RegionUiState> = _uiState.asStateFlow()

    init {
        loadRegions()
    }

    private fun loadRegions() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            getRegionUseCase()
                .onSuccess { regions ->
                    _uiState.update {
                        it.copy(
                            regions = regions,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Errore nel caricamento delle regioni"
                        )
                    }
                }
        }
    }

    fun retry() {
        loadRegions()
    }
}