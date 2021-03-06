package com.android.habibi.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.habibi.core.data.source.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieCatalogueDatabase: RoomDatabase() {
    abstract fun movieCatalogueDao(): MovieCatalogueDao
}