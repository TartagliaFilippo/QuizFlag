package com.projects.quizflags.ui.viewModel

import androidx.lifecycle.ViewModel
import com.projects.quizflags.domain.model.Region
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
open class RegionViewModel @Inject constructor() : ViewModel() {
    private val _regions = MutableStateFlow(
        listOf(
            Region(
                code = "eu",
                nameIta = "Europa",
                nameEng = "Europe",
                nameEsp = "Europa"
            ),
            Region(
                code = "as",
                nameIta = "Asia",
                nameEng = "Asia",
                nameEsp = "Asia"
            ),
            Region(
                code = "af",
                nameIta = "Africa",
                nameEng = "Africa",
                nameEsp = "África"
            ),
            Region(
                code = "na",
                nameIta = "Nord America",
                nameEng = "North America",
                nameEsp = "América del norte"
            ),
            Region(
                code = "sa",
                nameIta = "Sud America",
                nameEng = "South America",
                nameEsp = "Sudamérica"
            ),
            Region(
                code = "oc",
                nameIta = "Oceania",
                nameEng = "Oceania",
                nameEsp = "Oceanía"
            ),
            Region(
                code = "an",
                nameIta = "Antartide",
                nameEng = "Antarctica",
                nameEsp = "Antártida"
            )
        )
    )

    open val regions: StateFlow<List<Region>> = _regions.asStateFlow()
}