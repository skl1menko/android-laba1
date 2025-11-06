#!/bin/bash

# Скрипт для перевірки та налаштування середовища

echo "==================================="
echo "Перевірка середовища для проєкту"
echo "==================================="
echo ""

# Перевірка Java
echo "1. Перевірка версії Java..."
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | awk -F '"' '{print $2}')
    echo "   ✓ Java встановлено: $JAVA_VERSION"

    # Перевірка, чи це Java 17
    if [[ "$JAVA_VERSION" == 17* ]]; then
        echo "   ✓ Java 17 - відмінно!"
    elif [[ "$JAVA_VERSION" == 11* ]]; then
        echo "   ⚠ Java 11 - працюватиме, але рекомендується Java 17"
    elif [[ "$JAVA_VERSION" == 25* ]]; then
        echo "   ❌ Java 25 - не підтримується Kotlin 2.0.21"
        echo "   📝 Встановіть Java 17: brew install openjdk@17"
        echo "   📝 Або використайте Android Studio з вбудованою JDK"
    else
        echo "   ⚠ Незвичайна версія Java"
    fi
else
    echo "   ❌ Java не знайдено"
    echo "   📝 Встановіть Java 17: brew install openjdk@17"
fi
echo ""

# Перевірка всіх встановлених версій Java
echo "2. Доступні версії Java..."
if command -v /usr/libexec/java_home &> /dev/null; then
    /usr/libexec/java_home -V 2>&1 | grep -v "Matching" | grep -v "^/" | while read line; do
        echo "   $line"
    done
else
    echo "   Не вдалося визначити"
fi
echo ""

# Перевірка Android SDK
echo "3. Перевірка Android SDK..."
if [ -d "$HOME/Library/Android/sdk" ]; then
    echo "   ✓ Android SDK знайдено: $HOME/Library/Android/sdk"
elif [ -n "$ANDROID_HOME" ]; then
    echo "   ✓ Android SDK знайдено: $ANDROID_HOME"
else
    echo "   ⚠ Android SDK не знайдено"
    echo "   📝 Встановіть через Android Studio"
fi
echo ""

# Перевірка adb
echo "4. Перевірка Android Debug Bridge (adb)..."
if command -v adb &> /dev/null; then
    echo "   ✓ adb встановлено"
    echo "   Підключені пристрої:"
    adb devices | tail -n +2 | while read line; do
        if [ -n "$line" ]; then
            echo "   - $line"
        fi
    done
else
    echo "   ⚠ adb не знайдено в PATH"
fi
echo ""

# Перевірка Gradle
echo "5. Перевірка Gradle..."
if [ -f "./gradlew" ]; then
    echo "   ✓ Gradle wrapper знайдено"
    echo "   Спроба визначити версію..."
    ./gradlew --version | grep "Gradle" | head -n 1
else
    echo "   ❌ gradlew не знайдено"
fi
echo ""

# Рекомендації
echo "==================================="
echo "Рекомендації для запуску:"
echo "==================================="
echo ""
echo "ВАРІАНТ 1 (Рекомендовано): Використайте Android Studio"
echo "  1. Відкрийте проєкт в Android Studio"
echo "  2. Дозвольте синхронізувати Gradle"
echo "  3. Натисніть 'Run' (зелена кнопка)"
echo ""
echo "ВАРІАНТ 2: Встановіть Java 17"
echo "  brew install openjdk@17"
echo "  export JAVA_HOME=\$(/usr/libexec/java_home -v 17)"
echo ""
echo "ВАРІАНТ 3: Використайте JDK з Android Studio"
echo "  export JAVA_HOME=/Applications/Android\\ Studio.app/Contents/jbr/Contents/Home"
echo ""
echo "==================================="
echo "Документація:"
echo "==================================="
echo "  README.md - Загальний опис проєкту"
echo "  SETUP_INSTRUCTIONS.md - Детальні інструкції"
echo "  app/src/main/java/.../Documentation.kt - Технічна документація"
echo ""

