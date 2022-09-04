package com.hasanmuslemani.tvtracker.presentation

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hasanmuslemani.tvtracker.data.repository.TVSearchRepositoryImpl
import com.hasanmuslemani.tvtracker.data.repository.TVShowDetailsRepositoryImpl
import com.hasanmuslemani.tvtracker.presentation.tv_details.TVDetailsScreen
import com.hasanmuslemani.tvtracker.presentation.tv_search.TVSearchScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.TVSearchScreen.route) {
        composable(route = Screen.TVSearchScreen.route) {
            TVSearchScreen(navController = navController)
        }
        composable(route = Screen.TVDetailsScreen.route + "/{tvShowId}") {
            TVDetailsScreen(navController = navController)
        }
    }
}