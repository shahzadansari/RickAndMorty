package com.example.character_interactors

import com.example.core.ApiException
import com.example.core.DataState
import com.example.core.toApiException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import kotlin.coroutines.CoroutineContext

fun <T> Flow<DataState<T>>.safeDataStateFlow(coroutineContext: CoroutineContext = Dispatchers.IO, autoRetry: Boolean = true): Flow<DataState<T>> =
    retryWhen { cause, attempt -> autoRetry && canRetry(cause, attempt) }
        .catch { (it as? Exception)?.toApiException()?.let { exception -> emit(DataState.Error(exception)) } }
        .flowOn(coroutineContext)
        .onStart { emit(DataState.Loading()) }

private suspend fun canRetry(cause: Throwable, attempt: Long, retries: Int = 3, delayBetweenRetries: Long = 500): Boolean {
    val isNetworkException = (cause as? Exception)?.toApiException() == ApiException.Network
    val canRetry = isNetworkException && attempt < retries
    return if (canRetry) {
        delay(delayBetweenRetries)
        true
    } else {
        false
    }
}

fun <T> Flow<DataState<T>>.onLoading(onLoading: (Boolean) -> Unit): Flow<DataState<T>> {
    return onEach { onLoading(it is DataState.Loading) }
}

fun <T> Flow<DataState<T>>.onSuccess(onSuccess: (T) -> Unit): Flow<DataState<T>> {
    return onEach {
        if (it is DataState.Success) {
            onSuccess(it.data)
        }
    }
}

fun <T> Flow<DataState<T>>.onError(onError: (ApiException) -> Unit): Flow<DataState<T>> {
    return onEach {
        if (it is DataState.Error) {
            onError(it.cause)
        }
    }
}