package com.projects.quizflags

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.projects.quizflags.navigation.NavGraph

@Composable
fun QuizApp() {
    val navController = rememberNavController()
    NavGraph(navController)
}