package com.example.core

sealed class DataState<T>(open val data: T? = null, open val cause: ApiException? = null) {
    data class Loading<T>(override val data: T? = null) : DataState<T>()
    data class Success<T>(override val data: T) : DataState<T>(data = data)
    data class Error<T>(override val cause: ApiException) : DataState<T>(cause = cause)
}

sealed class ApiException(open val statusCode: Int = -1, open val errorMsg: String) : Exception(errorMsg) {
    data class Generic(override val errorMsg: String = "Something went wrong. Please try again!") : ApiException(errorMsg = errorMsg)
    data class HttpError(override val statusCode: Int, override val errorMsg: String = "Request could not be processed! Code :$statusCode") : ApiException(errorMsg = errorMsg)
    data object Network : ApiException(errorMsg = "Failed to connect. Please try again!")
}