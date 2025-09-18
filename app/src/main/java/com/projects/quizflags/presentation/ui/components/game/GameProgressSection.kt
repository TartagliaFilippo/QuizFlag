package com.projects.quizflags.presentation.ui.components.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.projects.quizflags.domain.model.GameMode

@Composable
fun GameProgressSection(
    score: Int,
    round: Int,
    totalQuestions: Int,
    gameMode: GameMode,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Punteggio",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
                Text(
                    text = "$score",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Domanda",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
                Text(
                    text = "$round/$totalQuestions",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            when (gameMode) {
                is GameMode.ClassicGame -> {
                    LinearProgressIndicator(
                    progress = { round.toFloat() / totalQuestions.toFloat() },
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = ProgressIndicatorDefaults.linearTrackColor,
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$round / $totalQuestions",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                is GameMode.RegionGame -> {
                    LinearProgressIndicator(
                    progress = { round.toFloat() / totalQuestions.toFloat() },
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = ProgressIndicatorDefaults.linearTrackColor,
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$round / $totalQuestions",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                is GameMode.SurvivalGame -> {
                    Text(
                        text = "Modalità Sopravvivenza",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Domanda #$round",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Percentuale (eccetto per la modalità sopravvivenza)
        if (gameMode !is GameMode.SurvivalGame && totalQuestions > 0) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val percentage = (score * 100) / totalQuestions
                Text(
                    text = "$percentage%",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = getPerformanceColor(percentage)
                )
                Text(
                    text = "Accuratezza",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun getPerformanceColor(percentage: Int): Color {
    return when {
        percentage >= 80 -> Color(0xFF4CAF50)
        percentage >= 60 -> Color(0xFFFF9800)
        else -> Color(0xFFF44336)
    }
}