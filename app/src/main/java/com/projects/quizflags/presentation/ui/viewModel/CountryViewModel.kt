package com.projects.quizflags.presentation.ui.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.quizflags.domain.usecase.GetCountriesByRegionUseCase
import com.projects.quizflags.domain.usecase.GetRegionUseCase
import com.projects.quizflags.presentation.state.CountryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class CountryViewModel @Inject constructor(
    private val getCountriesByRegionUseCase: GetCountriesByRegionUseCase,
    private val getRegionUseCase: GetRegionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val regionCode: String = savedStateHandle.get<String>("regionCode") ?: ""

    private val _uiState = MutableStateFlow(CountryUiState(isLoading = true))
    val uiState: StateFlow<CountryUiState> = _uiState.asStateFlow()

    init {
        loadCountries(regionCode)
    }

    private fun loadCountries(regionCode: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            // Carico le informazioni sul continente e i paesi in parallelo
            val regionsDeferred = async { getRegionUseCase() }
            val countriesDeferred = async { getCountriesByRegionUseCase(regionCode) }

            try {
                val regionsResult = regionsDeferred.await()
                val countriesResult = countriesDeferred.await()

                val selectedRegion = regionsResult.getOrNull()
                    ?.find { it.code == regionCode }

                countriesResult
                    .onSuccess { countries ->
                        _uiState.update {
                            it.copy(
                                countries = countries,
                                selectedRegion = selectedRegion,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                    .onFailure { exception ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = exception.message ?: "Errore nel caricamento dei paesi"
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Errore generico"
                    )
                }
            }
        }
    }

    fun retry() {
        loadCountries(regionCode)
    }
}