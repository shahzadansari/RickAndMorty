package com.example.core

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend inline fun <reified T> HttpClient.safeRequest(block: HttpRequestBuilder.() -> Unit): ApiResponse<T> = try {
    val response = request { block() }
    ApiResponse.Success(response.body())
} catch (exception: Exception) {
    ApiResponse.Error(exception.toApiException())
} catch (throwable: Throwable) {
    ApiResponse.Error(ApiException.Generic(throwable.localizedMessage))
}

sealed class ApiResponse<T>(open val data: T? = null, open val cause: ApiException? = null) {
    class Success<T>(override val data: T) : ApiResponse<T>(data)
    class Error<T>(override val cause: ApiException) : ApiResponse<T>(cause = cause)
}

fun Exception.toApiException(): ApiException {
    return when (this) {
        is ApiException -> this
        is UnknownHostException, is ConnectException, is SocketTimeoutException -> ApiException.Network
        is ClientRequestException -> ApiException.HttpError(statusCode = this.response.status.value, errorMsg = this.message)
        is ServerResponseException -> ApiException.HttpError(statusCode = this.response.status.value, errorMsg = this.message)
        is IOException -> ApiException.Network
        is HttpExceptions -> ApiException.HttpError(statusCode = this.response.status.value, errorMsg = this.message)
        else -> ApiException.Generic(localizedMessage)
    }
}

class HttpExceptions(
    response: HttpResponse,
    failureReason: String?,
    cachedResponseText: String
) : ResponseException(response, cachedResponseText) {
    override val message: String = "Status: ${response.status}" + " Failure: $failureReason"
}