package com.android.habibi.core.data.source.local

import com.android.habibi.core.data.source.local.entity.MovieEntity
import com.android.habibi.core.data.source.local.room.MovieCatalogueDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource constructor(private val movieCatalogueDao: MovieCatalogueDao){
    fun getFavorite(): Flow<List<MovieEntity>> = movieCatalogueDao.getFavoriteMovie()

    fun isFavoriteMovie(id: String): Flow<MovieEntity> = movieCatalogueDao.isFavoriteMovie(id)

    suspend fun insertMovie(movies: MovieEntity) = movieCatalogueDao.insertMovie(movies)

    suspend fun deleteFavoriteMovie(movieEntity: MovieEntity) = movieCatalogueDao.deleteMovie(movieEntity)
}