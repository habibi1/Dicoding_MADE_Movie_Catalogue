package id.go.jakarta.dicoding_made_moviecatalogue.core.domain.usecase

import id.go.jakarta.dicoding_made_moviecatalogue.core.data.Resource
import id.go.jakarta.dicoding_made_moviecatalogue.core.domain.model.Movie
import id.go.jakarta.dicoding_made_moviecatalogue.core.domain.repository.IMovieRepository
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