package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// Перший підекран з попередньою розміткою + кнопка переходу
@Composable
fun HomeMainSubScreen(navController: NavHostController) {
    // Використовуємо remember - стан не зберігається при зміні орієнтації
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

        // Кнопка переходу на другий підекран
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate(HomeSubDestinations.DETAILS) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text("➡️ Перейти до деталей", fontSize = 18.sp)
        }
    }
}

