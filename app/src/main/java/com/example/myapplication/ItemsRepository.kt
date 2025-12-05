package com.example.myapplication

// Репозиторій для надання тестових даних
class ItemsRepository {
    fun getItems(): List<ListItem> {
        return listOf(
            ListItem.Category(1, "Електроніка", "📱"),
            ListItem.Product(1, "iPhone 15", 999.99, "Новий смартфон від Apple"),
            ListItem.Product(2, "Samsung Galaxy S24", 849.99, "Флагманський Android"),
            ListItem.Category(2, "Одяг", "👕"),
            ListItem.Product(3, "Футболка", 29.99, "Бавовняна футболка"),
            ListItem.Product(4, "Джинси", 59.99, "Сині джинси"),
            ListItem.Category(3, "Книги", "📚"),
            ListItem.Product(5, "Kotlin Programming", 45.00, "Вивчення Kotlin"),
            ListItem.Product(6, "Android Development", 55.00, "Розробка Android-додатків"),
            ListItem.Category(4, "Продукти", "🍎"),
            ListItem.Product(7, "Яблука", 2.99, "Свіжі яблука, 1 кг"),
            ListItem.Product(8, "Молоко", 1.49, "Молоко 1 л")
        )
    }
}

