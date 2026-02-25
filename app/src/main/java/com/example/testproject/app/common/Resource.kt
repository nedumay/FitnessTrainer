package com.example.testproject.app.common

/**
 * @author Nedumayy (Samim)
 * Класс для обработки состояния обработки сети
 */
sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}
