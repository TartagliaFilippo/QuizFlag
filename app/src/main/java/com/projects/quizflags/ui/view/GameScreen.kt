package com.projects.quizflags.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.projects.quizflags.data.model.GameMode
import com.projects.quizflags.ui.components.CountryButton
import com.projects.quizflags.ui.viewModel.GameViewModel

@SuppressLint("LocalContextResourcesRead")
@Composable
fun GameScreen(
    navController: NavHostController,
    gameViewModel: GameViewModel,
    mode: GameMode
) {
    val context = LocalContext.current

    val question by gameViewModel.currentQuestion.collectAsState()
    val score by gameViewModel.score.collectAsState()
    val round by gameViewModel.round.collectAsState()
    val isGameOver by gameViewModel.isGameOver.collectAsState()


    LaunchedEffect(mode) {
        gameViewModel.startGame(mode)
    }

    if (isGameOver) {
        LaunchedEffect(Unit) {
            navController.navigate("endGame/$score") {
                popUpTo("game") { inclusive = true }
            }
        }
        return
    }

    @Suppress("DiscouragedApi")
    val imageResId = remember(question) {
        question?.correctAnswer?.pathImg
            ?.substringBeforeLast(".")
            ?.let { name ->
                context.resources.getIdentifier(name, "drawable", context.packageName)
            } ?: 0
    }


    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var isCorrectAnswer by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(question) {
        selectedAnswer = null
        isCorrectAnswer = null
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) { with(this) {
        when {
            maxWidth < 600.dp -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.LightGray),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Image(
                    //     modifier = Modifier.fillMaxSize(),
                    //     painter = painterResource(id = R.drawable.background_green),
                    //     contentDescription = null,
                    //     contentScale = ContentScale.Crop
                    // )
                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .weight(1f)
                            .padding(horizontal = 16.dp, vertical = 24.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            if (imageResId != 0) {
                                Image(
                                    painter = painterResource(imageResId),
                                    contentDescription = question?.correctAnswer?.nameIta,
                                    contentScale = ContentScale.Fit
                                )
                            }

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                text = "Indovina la bandiera del paese",
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        question?.let { quest ->
                            quest.options.forEach { option ->
                                CountryButton(
                                    text = option.nameIta,
                                    isCorrect = when {
                                        selectedAnswer == null -> null
                                        selectedAnswer == option.code && isCorrectAnswer == true -> true
                                        selectedAnswer == option.code && isCorrectAnswer == false ->false
                                        else -> null
                                    },
                                    onClick = {
                                        if (selectedAnswer == null) {
                                            selectedAnswer = option.code
                                            isCorrectAnswer = option.code == quest.correctAnswer.code

                                            gameViewModel.submitAnswer(option)
                                        }
                                    }
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text("Score: $score")

                        Text("Question: $round/10")
                    }
                }
            }
            maxWidth < 840.dp -> {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Cyan),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Layout Tablet", style = MaterialTheme.typography.headlineSmall)
                }
            }
            else -> {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Green),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Layout Desktop", style = MaterialTheme.typography.headlineSmall)
                }
            }
        }
    }
    }
}