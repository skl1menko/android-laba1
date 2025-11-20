package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

// Навігаційний граф
@Composable
fun AppNavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.HOME.name,
        modifier = modifier
    ) {
        composable(AppDestinations.HOME.name) {
            HomeScreen()
        }
        composable(AppDestinations.PROFILE.name) {
            ProfileScreen()
        }
        composable(AppDestinations.SETTINGS.name) {
            SettingsScreen()
        }
    }
}


