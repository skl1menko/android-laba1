package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

// Enum клас для елементів навігації
enum class AppDestinations(val title: String, val icon: ImageVector) {
    HOME("Головна", Icons.Filled.Home),
    PROFILE("Профіль", Icons.Filled.Person),
    SETTINGS("Налаштування", Icons.Filled.Settings)
}

// ViewModel для демонстрації збереження стану
class HomeViewModel : ViewModel() {
    var labelText by mutableStateOf("Натисніть кнопку для зміни тексту")
}

class ProfileViewModel : ViewModel() {
    var labelText by mutableStateOf("Вітаємо у профілі!")
}

class SettingsViewModel : ViewModel() {
    var labelText by mutableStateOf("Налаштування додатку")
}

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

// Екран "Головна" з remember
@Composable
fun HomeScreen() {
    // Використовуємо remember - стан НЕ зберігається при зміні орієнтації
    var rememberText by remember { mutableStateOf("Текст з remember (НЕ зберігається)") }
    var clickCount by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "🏠 Головна сторінка",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = rememberText,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Кількість натискань: $clickCount",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Button(
                    onClick = {
                        clickCount++
                        rememberText = "Кнопку натиснуто $clickCount раз(ів)!"
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Натисни мене (remember)", fontSize = 16.sp)
                }
            }
        }

        Text(
            text = "💡 Підказка: При зміні орієнтації екрану дані з remember будуть втрачені",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

// Екран "Профіль" з rememberSaveable
@Composable
fun ProfileScreen() {
    // Використовуємо rememberSaveable - стан ЗБЕРІГАЄТЬСЯ при зміні орієнтації
    var saveableText by rememberSaveable { mutableStateOf("Текст з rememberSaveable (ЗБЕРІГАЄТЬСЯ)") }
    var visitCount by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        visitCount++
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "👤 Профіль користувача",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = saveableText,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Text(
                    text = "Відвідувань сторінки: $visitCount",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Button(
                    onClick = {
                        saveableText = "Оновлено ${System.currentTimeMillis() % 10000}"
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text("Оновити профіль (rememberSaveable)")
                }
            }
        }

        Text(
            text = "💡 Підказка: При зміні орієнтації екрану дані з rememberSaveable залишаються",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

// Екран "Налаштування" з ViewModel
@Composable
fun SettingsScreen(viewModel: SettingsViewModel = viewModel()) {
    // Використовуємо ViewModel - стан ЗБЕРІГАЄТЬСЯ при зміні орієнтації
    var localCounter by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "⚙️ Налаштування",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = viewModel.labelText,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Text(
                    text = "Лічильник (ViewModel): $localCounter",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Button(
                    onClick = {
                        localCounter++
                        viewModel.labelText = "Змінено через ViewModel! Лічильник: $localCounter"
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Змінити (ViewModel)", fontSize = 18.sp)
                }
            }
        }

        Text(
            text = "💡 Підказка: ViewModel зберігає стан навіть при зміні конфігурації (орієнтація, мова тощо)",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

// Navigation Bar
@Composable
fun AppBottomNavigationBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        AppDestinations.entries.forEach { destination ->
            NavigationBarItem(
                selected = currentRoute == destination.name,
                onClick = {
                    navController.navigate(destination.name) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.title
                    )
                },
                label = { Text(destination.title) }
            )
        }
    }
}

