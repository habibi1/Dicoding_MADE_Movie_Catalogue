package com.android.habibi.core.domain.usecase

import com.android.habibi.core.data.Resource
import com.android.habibi.core.domain.model.Movie
import com.android.habibi.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor constructor(
    private val movieRepository: IMovieRepository
) : IMovieUseCase {
    override fun getAllMovie(): Flow<Resource<List<Movie>>> =
        movieRepository.getAllMovie()

    override fun getFavoriteMovie(): Flow<List<Movie>> =
        movieRepository.getFavoriteMovie()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) =
        movieRepository.setFavoriteMovie(
            movie,
            state
        )
}