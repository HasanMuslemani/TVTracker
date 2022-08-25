package com.hasanmuslemani.tvtracker.presentation

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
}
