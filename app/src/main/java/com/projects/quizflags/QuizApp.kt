package com.projects.quizflags

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.projects.quizflags.presentation.navigation.NavGraph

@Composable
fun QuizApp() {
    val navController = rememberNavController()
    NavGraph(navController)
}