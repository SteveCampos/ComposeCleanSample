package com.stevecampos.composecleansample.data.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T> emitApiAsResult(f: suspend () -> Response<T>): Flow<Result<T>> = flow {
    val r: Result<T> = getResultFromApiCall(f)
    emit(r)
}

suspend fun <T> getResultFromApiCall(apiCall: suspend () -> Response<T>): Result<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!)
        } else {
            Result.failure<T>(Throwable("Http error: ${response.code()}, message: ${response.message()}"))
        }
    } catch (t: Throwable) {
        Result.failure<T>(t)
    }
}