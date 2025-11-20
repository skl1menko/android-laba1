package com.example.myapplication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel : ViewModel() {
    var labelText by mutableStateOf("Натисніть кнопку для зміни тексту")
}

class ProfileViewModel : ViewModel() {
    var labelText by mutableStateOf("Вітаємо у профілі!")
}

class SettingsViewModel : ViewModel() {
    private var _labelText by mutableStateOf("Налаштування додатку")
        private set
    val labelText: StateFlow<String>
        get() = kotlinx.coroutines.flow.flowOf(_labelText).stateIn(
            viewModelScope,
            kotlinx.coroutines.flow.SharingStarted.Lazily,
            _labelText
        )

    var counter by mutableStateOf(0)
        private set

    fun incrementCounter() {
        counter++
        _labelText = "Змінено через ViewModel! Лічильник: $counter"
    }
}

