package com.android.habibi.core.data

import com.android.habibi.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success ->
                emitAll(loadFromNetwork(apiResponse.data).map {
                    Resource.Success(it)
                })
            is ApiResponse.Error ->
                emit(Resource.Error(apiResponse.errorMessage))
        }
    }

    protected abstract fun loadFromNetwork(data: RequestType): Flow<ResultType>

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    fun asFlow(): Flow<Resource<ResultType>> = result
}