package com.android.habibi.core.data.source.remote

import com.android.habibi.core.data.source.remote.network.ApiResponse
import com.android.habibi.core.data.source.remote.network.ApiService
import com.android.habibi.core.data.source.remote.response.MovieDetailResponse
import com.android.habibi.core.data.source.remote.response.ResultsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource constructor(private val apiService: ApiService) {

    suspend fun getMovieNowPlaying(): Flow<ApiResponse<List<ResultsItem>>> =
        flow {
            try {
                val response = apiService.getMovieNowPlaying()
                emit(ApiResponse.Success(response.results))
            } catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getDetailMovie(movieId: String): Flow<ApiResponse<MovieDetailResponse>> =
        flow {
            try {
                val response = apiService.getDetailMovie(movieId)
                emit(ApiResponse.Success(response))
            } catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

}