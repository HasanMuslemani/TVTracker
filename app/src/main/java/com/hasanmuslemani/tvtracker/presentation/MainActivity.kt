package com.hasanmuslemani.tvtracker.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hasanmuslemani.tvtracker.presentation.common.BottomNavItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStackEntry?.destination?.route
            Scaffold(
                bottomBar = {
                    if (bottomNavRoutes.contains(currentDestination)) {
                        BottomNavBar(
                            items = listOf(
                                BottomNavItem(
                                    "Search",
                                    Screen.TVSearchScreen.route,
                                    icon = Icons.Default.Search
                                ),
                                BottomNavItem(
                                    "Watchlist",
                                    Screen.WatchlistScreen.route,
                                    icon = Icons.Default.Bookmark
                                )
                            ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route) {
                                    navController.graph.startDestinationRoute?.let { route ->
                                        popUpTo(route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            ) {
                Navigation(navController)
            }
        }
    }

    @Composable
    fun BottomNavBar(
        items: List<BottomNavItem>,
        navController: NavController,
        modifier: Modifier = Modifier,
        onItemClick: (BottomNavItem) -> Unit
    ) {
        val backStackEntry = navController.currentBackStackEntryAsState()
        BottomNavigation(
            modifier = modifier,
            backgroundColor = Color(0xFF001529),
            elevation = 5.dp
        ) {
            items.forEach { item ->
                val selected = item.route == backStackEntry.value?.destination?.route
                BottomNavigationItem(
                    selected = selected,
                    onClick = { onItemClick(item) },
                    selectedContentColor = Color(0xFFfc5203),
                    unselectedContentColor = Color(0xFFc2c2c2),
                    icon = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name,
                            )
                            Text(
                                color = if(selected) Color(0xFFfc5203) else Color(0xFFc2c2c2),
                                text = item.name
                            )
                        }
                    }
                )
            }
        }
    }

}