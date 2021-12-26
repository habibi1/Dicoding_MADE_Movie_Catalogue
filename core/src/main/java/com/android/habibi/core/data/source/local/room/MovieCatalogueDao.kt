package com.android.habibi.core.data.source.local.room

import androidx.room.*
import com.android.habibi.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieCatalogueDao {
    @Query("SELECT * FROM movie_entities")
    fun getMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * from movie_entities WHERE favorite = 1")
    fun getFavoriteMovie(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)
}