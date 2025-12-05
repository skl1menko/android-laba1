package com.example.myapplication

import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.CategoryEntity
import com.example.myapplication.database.ProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

// Репозиторій для роботи з базою даних
class ItemsRepository(private val database: AppDatabase) {

    // Отримуємо об'єднаний список категорій і продуктів
    fun getItemsFlow(): Flow<List<ListItem>> {
        return combine(
            database.categoryDao().getAllCategories(),
            database.productDao().getAllProducts()
        ) { categories, products ->
            buildList {
                categories.forEach { category ->
                    // Додаємо категорію
                    add(ListItem.Category(
                        id = category.id,
                        name = category.name,
                        icon = category.icon
                    ))
                    // Додаємо продукти цієї категорії
                    products
                        .filter { it.categoryId == category.id }
                        .forEach { product ->
                            add(ListItem.Product(
                                id = product.id,
                                categoryId = product.categoryId,
                                name = product.name,
                                price = product.price,
                                description = product.description
                            ))
                        }
                }
            }
        }
    }

    // Категорії
    suspend fun insertCategory(name: String, icon: String): Long {
        return database.categoryDao().insertCategory(
            CategoryEntity(name = name, icon = icon)
        )
    }

    suspend fun updateCategory(id: Int, name: String, icon: String) {
        database.categoryDao().updateCategory(
            CategoryEntity(id = id, name = name, icon = icon)
        )
    }

    suspend fun deleteCategory(id: Int) {
        val category = database.categoryDao().getCategoryById(id)
        category?.let {
            database.categoryDao().deleteCategory(it)
        }
    }

    // Продукти
    suspend fun insertProduct(categoryId: Int, name: String, price: Double, description: String): Long {
        return database.productDao().insertProduct(
            ProductEntity(
                categoryId = categoryId,
                name = name,
                price = price,
                description = description
            )
        )
    }

    suspend fun updateProduct(id: Int, categoryId: Int, name: String, price: Double, description: String) {
        database.productDao().updateProduct(
            ProductEntity(
                id = id,
                categoryId = categoryId,
                name = name,
                price = price,
                description = description
            )
        )
    }

    suspend fun deleteProduct(id: Int) {
        val product = database.productDao().getProductById(id)
        product?.let {
            database.productDao().deleteProduct(it)
        }
    }

    fun getAllCategories(): Flow<List<CategoryEntity>> {
        return database.categoryDao().getAllCategories()
    }
}

