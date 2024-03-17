package com.hasanmuslemani.tvtracker.presentation.tv_watchlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hasanmuslemani.tvtracker.presentation.Screen
import com.hasanmuslemani.tvtracker.presentation.tv_search.TVSearchItem

@Composable
fun WatchlistScreen(
    navController: NavController,
    viewModel: WatchlistViewModel
) {
    val state = viewModel.watchlistState.value

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color(19, 28, 48))
                .fillMaxSize()
        ) {
            if(state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
            else if (state.error.isNotBlank()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        state.error,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 24.sp
                    )
                }
            }
            else {
                val watchlist = state.watchlist
                if (watchlist.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "Your watchlist is empty. Add TV Shows to your watchlist to view them here.",
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(watchlist) { tvShow ->
                            TVSearchItem(tvSearch = tvShow) {
                                navController.navigate(Screen.TVDetailsScreen.route + "/${tvShow.id}")
                            }
                        }
                    }
                }
            }
        }
    }
}