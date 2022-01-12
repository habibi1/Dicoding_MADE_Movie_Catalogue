package com.android.habibi.core.domain.usecase

import com.android.habibi.core.data.Resource
import com.android.habibi.core.data.source.local.entity.MovieEntity
import com.android.habibi.core.domain.model.Movie
import com.android.habibi.core.domain.model.MovieDetail as MovieDetailDomain
import com.android.habibi.core.domain.repository.IMovieRepository
//import com.android.habibi.core.ui.model.MovieDetail as MovieDetailPresentation
import kotlinx.coroutines.flow.Flow

class MovieInteractor constructor(
    private val movieRepository: IMovieRepository
) : IMovieUseCase {
    override fun getAllMovie(): Flow<Resource<List<Movie>>> =
        movieRepository.getAllMovie()

    override fun getDetailMovie(movieId: String): Flow<Resource<MovieDetailDomain>> =
        movieRepository.getDetailMovie(movieId)

    override fun getAllFavoriteMovie(): Flow<List<Movie>> =
        movieRepository.getAllFavoriteMovie()

    override suspend fun insertFavoriteMovie(movie: MovieDetailDomain) =
        movieRepository.insertFavoriteMovie(movie)

    override suspend fun deleteFavoriteMovie(movie: MovieDetailDomain): Int =
        movieRepository.deleteFavoriteMovie(movie)

    override fun isFavoriteMovie(id: String): Flow<MovieEntity> =
        movieRepository.isFavoriteMovie(id)
}