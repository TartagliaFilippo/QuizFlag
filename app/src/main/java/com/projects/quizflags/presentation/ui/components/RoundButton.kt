package com.projects.quizflags.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun RoundButton(
    modifier: Modifier = Modifier,
    sizeFraction: Float = 0.4f, // Percentuale della larghezza dello schermo
    backgroundColor: Color = Color.Blue,
    // backgroundColor: Color = MaterialTheme.colorScheme.primary, // TODO: Capire come gestire la tavolozze di colore
    icon: ImageVector,
    iconTint: Color = Color.White,
    contentDescription: String? = null,
    onClick: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val buttonSize = screenWidth * sizeFraction

    Box(
        modifier = modifier
            .size(buttonSize)
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconTint,
            modifier = Modifier.size(buttonSize * 0.5f)
        )
    }
}