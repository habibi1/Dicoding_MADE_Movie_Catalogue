package com.android.habibi.core.data.source.local.room

import androidx.room.*
import com.android.habibi.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieCatalogueDao {
    @Query("SELECT * from movie_entities")
    fun getFavoriteMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * from movie_entities WHERE id = :id")
    fun isFavoriteMovie(id: String): Flow<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity): Int
}