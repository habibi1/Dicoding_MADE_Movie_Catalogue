package id.go.jakarta.dicoding_made_moviecatalogue.core.domain.usecase

import id.go.jakarta.dicoding_made_moviecatalogue.core.data.Resource
import id.go.jakarta.dicoding_made_moviecatalogue.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieUseCase {
    fun getAllMovie(): Flow<Resource<List<Movie>>>
    fun getFavoriteMovie(): Flow<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
}