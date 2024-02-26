package com.jetbrains.dependoapp.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

object NetworkUtils {
    suspend fun <T> networkApiCall(apiFunction: suspend () -> T?): Flow<Result<T?>> {
        return flow {
            val response = apiFunction()
            emit(Result.success(response))
        }.catch { e ->
            emit(Result.failure(exception = e))
        }.flowOn(Dispatchers.IO).conflate()
    }

}

