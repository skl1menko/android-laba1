package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Основний екран "Головна" з внутрішньою навігацією
@Composable
fun HomeScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeSubDestinations.MAIN
    ) {
        composable(HomeSubDestinations.MAIN) {
            HomeMainSubScreen(navController = navController)
        }
        composable(HomeSubDestinations.DETAILS) {
            HomeDetailsSubScreen(navController = navController)
        }
    }
}


