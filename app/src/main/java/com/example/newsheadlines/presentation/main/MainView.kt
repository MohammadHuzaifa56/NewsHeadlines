package com.example.newsheadlines.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsheadlines.domain.model.News
import com.example.newsheadlines.presentation.detail.NewsDetailScreen
import com.example.newsheadlines.presentation.home.NewsHeadlinesListScreen
import com.example.newsheadlines.core.ScreenRoutes

@Composable
fun MainView() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = ScreenRoutes.HOME_SCREEN,
        ) {
            composable(route = ScreenRoutes.HOME_SCREEN) {
                NewsHeadlinesListScreen(
                    navController = navController
                )
            }

            composable(route = ScreenRoutes.DETAILS_SCREEN) {
                val item =
                    navController.previousBackStackEntry?.savedStateHandle?.get<News>("news")
                item?.let { it1 -> NewsDetailScreen(news = it1) }
            }
        }
    }
}