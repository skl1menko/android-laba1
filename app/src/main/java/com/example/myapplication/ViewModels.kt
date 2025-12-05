package com.example.myapplication

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.database.AppDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    var labelText by mutableStateOf("Натисніть кнопку для зміни тексту")

    private val database = AppDatabase.getDatabase(application)
    private val repository = ItemsRepository(database)

    val items: StateFlow<List<ListItem>> = repository.getItemsFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Операції з категоріями
    fun addCategory(name: String, icon: String) {
        viewModelScope.launch {
            repository.insertCategory(name, icon)
        }
    }

    fun updateCategory(id: Int, name: String, icon: String) {
        viewModelScope.launch {
            repository.updateCategory(id, name, icon)
        }
    }

    fun deleteCategory(id: Int) {
        viewModelScope.launch {
            repository.deleteCategory(id)
        }
    }

    // Операції з продуктами
    fun addProduct(categoryId: Int, name: String, price: Double, description: String) {
        viewModelScope.launch {
            repository.insertProduct(categoryId, name, price, description)
        }
    }

    fun updateProduct(id: Int, categoryId: Int, name: String, price: Double, description: String) {
        viewModelScope.launch {
            repository.updateProduct(id, categoryId, name, price, description)
        }
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            repository.deleteProduct(id)
        }
    }

    fun getCategories() = repository.getAllCategories()
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

