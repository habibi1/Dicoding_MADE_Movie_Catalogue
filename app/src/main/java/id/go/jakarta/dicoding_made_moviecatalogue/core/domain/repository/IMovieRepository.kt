package id.go.jakarta.dicoding_made_moviecatalogue.core.domain.repository

import id.go.jakarta.dicoding_made_moviecatalogue.core.data.Resource
import id.go.jakarta.dicoding_made_moviecatalogue.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getAllMovie(): Flow<Resource<List<Movie>>>
    fun getFavoriteMovie(): Flow<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
}