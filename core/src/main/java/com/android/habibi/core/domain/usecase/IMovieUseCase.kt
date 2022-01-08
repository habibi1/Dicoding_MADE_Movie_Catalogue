package com.android.habibi.core.domain.usecase

import com.android.habibi.core.data.Resource
import com.android.habibi.core.data.source.local.entity.MovieEntity
import com.android.habibi.core.domain.model.Movie
import com.android.habibi.core.domain.model.MovieDetail as MovieDetailDomain
//import com.android.habibi.core.ui.model.MovieDetail as MovieDetailPresentation
import kotlinx.coroutines.flow.Flow

interface IMovieUseCase {
    fun getAllMovie(): Flow<Resource<List<Movie>>>
    fun getDetailMovie(movieId: String): Flow<Resource<MovieDetailDomain>>
    fun getAllFavoriteMovie(): Flow<List<Movie>>
    suspend fun deleteFavoriteMovie(movie: MovieDetailDomain): Int
    suspend fun insertFavoriteMovie(movie: MovieDetailDomain)
    fun isFavoriteMovie(id: String): Flow<MovieEntity>
}