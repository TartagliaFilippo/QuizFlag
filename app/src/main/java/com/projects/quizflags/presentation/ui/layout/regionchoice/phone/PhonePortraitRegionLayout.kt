package com.projects.quizflags.presentation.ui.layout.regionchoice.phone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projects.quizflags.domain.model.Region
import com.projects.quizflags.presentation.ui.components.regionchoice.RegionCard
import com.projects.quizflags.presentation.ui.components.regionchoice.RegionTopBar
import com.projects.quizflags.presentation.ui.responsive.RegionCardStyle

@Composable
fun PhonePortraitRegionLayout(
    regions: List<Region>,
    onRegionSelected: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        RegionTopBar(
            title = "Scegli un Continente",
            onNavigateBack = onNavigateBack,
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(regions) { region ->
                RegionCard(
                    region = region,
                    onClick = { onRegionSelected(region.code) },
                    cardStyle = RegionCardStyle.FULL_WIDTH
                )
            }
        }
    }
}