package com.android.habibi.core.domain.repository

import com.android.habibi.core.data.Resource
import com.android.habibi.core.data.source.local.entity.MovieEntity
import com.android.habibi.core.domain.model.Movie
import com.android.habibi.core.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getAllMovie(): Flow<Resource<List<Movie>>>
    fun getDetailMovie(movieId: String): Flow<Resource<MovieDetail>>
    fun getAllFavoriteMovie(): Flow<List<Movie>>
    suspend fun deleteFavoriteMovie(movie: MovieDetail): Int
    suspend fun insertFavoriteMovie(movie: MovieDetail)
    fun isFavoriteMovie(id: String): Flow<MovieEntity>
}