package com.android.habibi.core.domain.usecase

import com.android.habibi.core.data.Resource
import com.android.habibi.core.data.source.local.entity.MovieEntity
import com.android.habibi.core.domain.model.Movie
import com.android.habibi.core.domain.model.MovieDetail
import com.android.habibi.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor constructor(
    private val movieRepository: IMovieRepository
) : IMovieUseCase {
    override fun getAllMovie(): Flow<Resource<List<Movie>>> =
        movieRepository.getAllMovie()

    override fun getDetailMovie(movieId: String): Flow<Resource<MovieDetail>> =
        movieRepository.getDetailMovie(movieId)

    override fun getAllFavoriteMovie(): Flow<List<Movie>> =
        movieRepository.getAllFavoriteMovie()

    override suspend fun insertFavoriteMovie(movie: MovieDetail) =
        movieRepository.insertFavoriteMovie(movie)

    override suspend fun deleteFavoriteMovie(movie: MovieDetail): Int =
        movieRepository.deleteFavoriteMovie(movie)

    override fun isFavoriteMovie(id: String): Flow<MovieEntity> =
        movieRepository.isFavoriteMovie(id)
}