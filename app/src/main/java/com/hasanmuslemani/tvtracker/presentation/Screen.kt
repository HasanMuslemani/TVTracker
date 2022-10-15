package com.hasanmuslemani.tvtracker.presentation

sealed class Screen(val route: String) {
    object TVSearchScreen : Screen("tv_search_screen")
    object TVDetailsScreen : Screen("tv_details_screen")
    object WatchlistScreen : Screen("watchlist_screen")
}
