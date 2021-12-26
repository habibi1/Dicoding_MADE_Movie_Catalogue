package com.android.habibi.core.domain.usecase

import com.android.habibi.core.data.Resource
import com.android.habibi.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieUseCase {
    fun getAllMovie(): Flow<Resource<List<Movie>>>
    fun getFavoriteMovie(): Flow<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
}