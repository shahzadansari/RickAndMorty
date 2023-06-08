package com.example.core

sealed class DataState<T>(open val data: T? = null, open val exception: Exception? = null) {
    data class Loading<T>(override val data: T? = null) : DataState<T>()
    data class Success<T>(override val data: T) : DataState<T>(data = data)
    data class Error<T>(override val exception: Exception?) : DataState<T>(exception = exception)
}