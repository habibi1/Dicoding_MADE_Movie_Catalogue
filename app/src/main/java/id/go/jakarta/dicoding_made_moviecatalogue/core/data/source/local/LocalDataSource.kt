package id.go.jakarta.dicoding_made_moviecatalogue.core.data.source.local

import id.go.jakarta.dicoding_made_moviecatalogue.core.data.source.local.entity.MovieEntity
import id.go.jakarta.dicoding_made_moviecatalogue.core.data.source.local.room.MovieCatalogueDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource constructor(private val movieCatalogueDao: MovieCatalogueDao){
    fun getMovie(): Flow<List<MovieEntity>> = movieCatalogueDao.getMovie()

    fun getFavorite(): Flow<List<MovieEntity>> = movieCatalogueDao.getFavoriteMovie()

    suspend fun insertMovie(movies: List<MovieEntity>) = movieCatalogueDao.insertMovie(movies)

    fun setFavorite(movie: MovieEntity, newState: Boolean){
        movie.isFavorite = newState
        movieCatalogueDao.updateMovie(movie)
    }
}