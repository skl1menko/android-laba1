package com.example.myapplication

// Sealed class для різних типів елементів списку
sealed class ListItem {
    data class Category(
        val id: Int,
        val name: String,
        val icon: String
    ) : ListItem()

    data class Product(
        val id: Int,
        val categoryId: Int,
        val name: String,
        val price: Double,
        val description: String
    ) : ListItem()
}

