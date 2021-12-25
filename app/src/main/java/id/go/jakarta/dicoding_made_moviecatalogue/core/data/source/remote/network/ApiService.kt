package id.go.jakarta.dicoding_made_moviecatalogue.core.data.source.remote.network

import id.go.jakarta.dicoding_made_moviecatalogue.BuildConfig
import id.go.jakarta.dicoding_made_moviecatalogue.core.data.source.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getMovieNowPlaying(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieResponse
}