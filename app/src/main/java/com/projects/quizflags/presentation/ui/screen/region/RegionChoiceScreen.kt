package com.projects.quizflags.presentation.ui.screen.region

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.projects.quizflags.domain.model.Region
import com.projects.quizflags.presentation.state.RegionUiState
import com.projects.quizflags.presentation.ui.components.common.ErrorContent
import com.projects.quizflags.presentation.ui.components.common.LoadingContent
import com.projects.quizflags.presentation.ui.layout.regionchoice.desktop.DesktopRegionLayout
import com.projects.quizflags.presentation.ui.layout.regionchoice.phone.PhoneLandscapeRegionLayout
import com.projects.quizflags.presentation.ui.layout.regionchoice.phone.PhonePortraitRegionLayout
import com.projects.quizflags.presentation.ui.layout.regionchoice.tablet.TabletLandscapeRegionLayout
import com.projects.quizflags.presentation.ui.layout.regionchoice.tablet.TabletPortraitRegionLayout
import com.projects.quizflags.presentation.ui.responsive.DeviceType
import com.projects.quizflags.presentation.ui.responsive.calculateWindowSize
import com.projects.quizflags.presentation.ui.responsive.toDeviceType
import com.projects.quizflags.ui.theme.QuizFlagsTheme

@Composable
fun RegionChoiceScreen(
    onRegionSelected: (String) -> Unit,
    onNavigateBack: () -> Unit,
    regionViewModel: RegionViewModel = hiltViewModel()
) {
    val uiState by regionViewModel.uiState.collectAsStateWithLifecycle()

    RegionChoiceContent(
        uiState = uiState,
        onRegionSelected = onRegionSelected,
        onNavigateBack = onNavigateBack,
        onRetryClick = {
            regionViewModel.retry()
        }
    )
}

@Composable
private fun RegionChoiceContent(
    uiState: RegionUiState,
    onRegionSelected: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onRetryClick: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        with(this) {
            val windowSize = calculateWindowSize(
                maxWidth = maxWidth,
                maxHeight = maxHeight
            )

            val deviceType = toDeviceType(
                windowSize,
                maxWidth,
                maxHeight
            )

            when {
                uiState.isLoading -> {
                    LoadingContent()
                }

                uiState.error != null -> {
                    ErrorContent(
                        error = uiState.error,
                        onRetry = onRetryClick,
                        onDismiss = onNavigateBack
                    )
                }

                else -> {
                    when (deviceType) {
                        DeviceType.PhonePortrait -> PhonePortraitRegionLayout(
                            regions = uiState.regions,
                            onRegionSelected = onRegionSelected,
                            onNavigateBack = onNavigateBack
                        )
                        DeviceType.PhoneLandscape -> PhoneLandscapeRegionLayout(
                            regions = uiState.regions,
                            onRegionSelected = onRegionSelected,
                            onNavigateBack = onNavigateBack
                        )
                        DeviceType.TabletPortrait -> TabletPortraitRegionLayout(
                            regions = uiState.regions,
                            onRegionSelected = onRegionSelected,
                            onNavigateBack = onNavigateBack
                        )
                        DeviceType.TabletLandscape -> TabletLandscapeRegionLayout(
                            regions = uiState.regions,
                            onRegionSelected = onRegionSelected,
                            onNavigateBack = onNavigateBack
                        )
                        DeviceType.Desktop -> DesktopRegionLayout(
                            regions = uiState.regions,
                            onRegionSelected = onRegionSelected,
                            onNavigateBack = onNavigateBack
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(name = "Phone Portrait", widthDp = 360, heightDp = 640)
@Preview(name = "Phone Landscape", widthDp = 640, heightDp = 360)
@Preview(name = "Tablet Portrait", widthDp = 800, heightDp = 1280)
@Preview(name = "Desktop", widthDp = 1200, heightDp = 800)
@Composable
fun PreviewRegionChoiceScreen() {
    val fakeRegions = listOf(
        Region("eu", "Europa", "Europe", "Europa", 44),
        Region("as", "Asia", "Asia", "Asia", 48),
        Region("af", "Africa", "Africa", "África", 54),
        Region("na", "Nord America", "North America", "América del Norte", 23),
        Region("sa", "Sud America", "South America", "Sudamérica", 12),
        Region("oc", "Oceania", "Oceania", "Oceanía", 16)
    )

    QuizFlagsTheme(
        windowSizeClass = WindowSizeClass.calculateFromSize(
            DpSize(360.dp, 640.dp)
        )
    ) {
        RegionChoiceContent(
            uiState = RegionUiState(
                regions = fakeRegions,
                isLoading = false,
                error = null
            ),
            onRegionSelected = { },
            onNavigateBack = { },
            onRetryClick = { }
        )
    }
}