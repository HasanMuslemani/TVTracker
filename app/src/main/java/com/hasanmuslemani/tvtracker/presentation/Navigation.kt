package com.hasanmuslemani.tvtracker.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hasanmuslemani.tvtracker.presentation.tv_details.TVDetailsScreen
import com.hasanmuslemani.tvtracker.presentation.tv_search.TVSearchScreen
import com.hasanmuslemani.tvtracker.presentation.tv_watchlist.WatchlistScreen
import com.hasanmuslemani.tvtracker.presentation.tv_watchlist.WatchlistViewModel

val bottomNavRoutes = listOf(
    Screen.TVSearchScreen.route,
    Screen.WatchlistScreen.route
)

@Composable
fun Navigation(
    navController: NavHostController
) {
    val watchlistViewModel: WatchlistViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = Screen.TVSearchScreen.route) {
        composable(route = Screen.TVSearchScreen.route) {
            TVSearchScreen(navController = navController)
        }
        composable(route = Screen.TVDetailsScreen.route + "/{tvShowId}") {
            TVDetailsScreen(navController = navController, watchlistViewModel)
        }
        composable(route = Screen.WatchlistScreen.route) {
            WatchlistScreen(navController = navController, watchlistViewModel)
        }
    }
}