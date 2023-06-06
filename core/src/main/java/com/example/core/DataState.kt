package com.example.core

sealed class DataState<T> {
    object Loading : DataState<Nothing>()
    data class Success<T>(val data: T) : DataState<T>()
    data class Error<T>(val throwable: Throwable) : DataState<T>()
}