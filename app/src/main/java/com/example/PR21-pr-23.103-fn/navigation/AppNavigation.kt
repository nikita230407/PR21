package com.example.PR21-pr-23.103-fn.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pz21last.ui.screens.AnalysesScreen
import com.example.pz21last.ui.screens.HistoryScreen
import com.example.pz21last.ui.screens.ProfileScreen

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    data object Analyses : Screen("analyses", "Анализы", Icons.Default.Analytics)
    data object History : Screen("history", "История", Icons.Default.History)
    data object Profile : Screen("profile", "Профиль", Icons.Default.Person)
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val screens = listOf(Screen.Analyses, Screen.History, Screen.Profile)

    Scaffold(
        bottomBar = {
            NavigationBar {
                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Analyses.route,
            modifier = modifier.padding(padding)
        ) {
            composable(Screen.Analyses.route) { AnalysesScreen() }
            composable(Screen.History.route) { HistoryScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
        }
    }
}
