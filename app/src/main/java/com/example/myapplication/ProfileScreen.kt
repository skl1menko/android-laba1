package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                        val currentTime = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault())
                            .format(java.util.Date())
                        saveableText = "Поточний час: $currentTime"
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text("Показати поточний час (rememberSaveable)")
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

