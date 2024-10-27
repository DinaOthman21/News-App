package com.example.news_app.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val throwable: Throwable? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(data: T? = null, message: String, throwable: Throwable? = null) : Resource<T>(data, message, throwable)
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>(null)
}