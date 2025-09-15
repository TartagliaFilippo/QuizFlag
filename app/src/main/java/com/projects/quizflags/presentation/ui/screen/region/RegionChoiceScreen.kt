package com.projects.quizflags.presentation.ui.screen.region

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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