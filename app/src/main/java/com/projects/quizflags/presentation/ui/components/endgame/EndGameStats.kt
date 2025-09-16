package com.projects.quizflags.presentation.ui.components.endgame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.presentation.ui.responsive.StatsOrientation

@Composable
fun EndGameStats(
    score: Int,
    totalQuestions: Int,
    gameMode: GameMode,
    orientation: StatsOrientation
) {
    val percentage = if (totalQuestions > 0) (score * 100) / totalQuestions else 0
    val correctAnswers = score
    val wrongAnswers = totalQuestions - score

    val arrangement = if (orientation == StatsOrientation.VERTICAL) {
        Arrangement.spacedBy(16.dp)
    } else {
        Arrangement.spacedBy(24.dp)
    }

    if (orientation == StatsOrientation.VERTICAL) {
        Column(
            verticalArrangement = arrangement,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StatItem(
                value = "Percentuale",
                label = "$percentage%",
                icon = Icons.Default.MoreVert // TODO: Sistema questa icona
            )
            StatItem(
                value = "Risposte corrette",
                label = correctAnswers.toString(),
                icon = Icons.Default.Check
            )
            StatItem(
                value = "Risposte errate",
                label = wrongAnswers.toString(),
                icon = Icons.Default.Close
            )
        }
    } else {
        Row(
            horizontalArrangement = arrangement
        ) {
            StatItem(
                value = "$percentage%",
                label = "Percentuale",
                icon = Icons.Default.MoreVert // TODO: Sistema questa icona
            )
            StatItem(
                value = correctAnswers.toString(),
                label = "Corrette",
                icon = Icons.Default.Check
            )
            StatItem(
                value = wrongAnswers.toString(),
                label = "Errate",
                icon = Icons.Default.Close
            )
        }
    }
}

@Composable
private fun StatItem(
    value: String,
    label: String,
    icon: ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
