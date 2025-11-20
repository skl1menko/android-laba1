package com.example.myapplication

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

// Enum клас для елементів навігації
enum class AppDestinations(val title: String, val icon: ImageVector) {
    HOME("Головна", Icons.Filled.Home),
    PROFILE("Профіль", Icons.Filled.Person),
    SETTINGS("Налаштування", Icons.Filled.Settings)
}

