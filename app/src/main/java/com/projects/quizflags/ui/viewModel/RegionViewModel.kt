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
                id = 1,
                code = "eu",
                nameIta = "Europa",
                nameEng = "Europe",
                nameEsp = "Europa"
            ),
            Region(
                id = 2,
                code = "as",
                nameIta = "Asia",
                nameEng = "Asia",
                nameEsp = "Asia"
            ),
            Region(
                id = 3,
                code = "af",
                nameIta = "Africa",
                nameEng = "Africa",
                nameEsp = "África"
            ),
            Region(
                id = 4,
                code = "na",
                nameIta = "Nord America",
                nameEng = "North America",
                nameEsp = "América del norte"
            ),
            Region(
                id = 5,
                code = "sa",
                nameIta = "Sud America",
                nameEng = "South America",
                nameEsp = "Sudamérica"
            ),
            Region(
                id = 6,
                code = "oc",
                nameIta = "Oceania",
                nameEng = "Oceania",
                nameEsp = "Oceanía"
            ),
            Region(
                id = 6,
                code = "an",
                nameIta = "Antartide",
                nameEng = "Antarctica",
                nameEsp = "Antártida"
            )
        )
    )

    open val regions: StateFlow<List<Region>> = _regions.asStateFlow()
}