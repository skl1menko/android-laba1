package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [CategoryEntity::class, ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database.categoryDao(), database.productDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(categoryDao: CategoryDao, productDao: ProductDao) {
            // Додаємо початкові дані
            val electronicsId = categoryDao.insertCategory(
                CategoryEntity(name = "Електроніка", icon = "📱")
            ).toInt()
            val clothingId = categoryDao.insertCategory(
                CategoryEntity(name = "Одяг", icon = "👕")
            ).toInt()
            val booksId = categoryDao.insertCategory(
                CategoryEntity(name = "Книги", icon = "📚")
            ).toInt()
            val foodId = categoryDao.insertCategory(
                CategoryEntity(name = "Продукти", icon = "🍎")
            ).toInt()

            // Додаємо продукти
            productDao.insertProduct(
                ProductEntity(
                    categoryId = electronicsId,
                    name = "iPhone 15",
                    price = 999.99,
                    description = "Новий смартфон від Apple"
                )
            )
            productDao.insertProduct(
                ProductEntity(
                    categoryId = electronicsId,
                    name = "Samsung Galaxy S24",
                    price = 849.99,
                    description = "Флагманський Android"
                )
            )
            productDao.insertProduct(
                ProductEntity(
                    categoryId = clothingId,
                    name = "Футболка",
                    price = 29.99,
                    description = "Бавовняна футболка"
                )
            )
            productDao.insertProduct(
                ProductEntity(
                    categoryId = clothingId,
                    name = "Джинси",
                    price = 59.99,
                    description = "Сині джинси"
                )
            )
            productDao.insertProduct(
                ProductEntity(
                    categoryId = booksId,
                    name = "Kotlin Programming",
                    price = 45.00,
                    description = "Вивчення Kotlin"
                )
            )
            productDao.insertProduct(
                ProductEntity(
                    categoryId = booksId,
                    name = "Android Development",
                    price = 55.00,
                    description = "Розробка Android-додатків"
                )
            )
            productDao.insertProduct(
                ProductEntity(
                    categoryId = foodId,
                    name = "Яблука",
                    price = 2.99,
                    description = "Свіжі яблука, 1 кг"
                )
            )
            productDao.insertProduct(
                ProductEntity(
                    categoryId = foodId,
                    name = "Молоко",
                    price = 1.49,
                    description = "Молоко 1 л"
                )
            )
        }
    }
}

