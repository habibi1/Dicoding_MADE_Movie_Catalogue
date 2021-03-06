package com.android.habibi.core.data.source.remote.network

import com.android.habibi.core.BuildConfig
import com.android.habibi.core.data.source.remote.response.MovieDetailResponse
import com.android.habibi.core.data.source.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getMovieNowPlaying(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieDetailResponse
}