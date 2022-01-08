package com.android.habibi.core.data.utils

import com.android.habibi.core.data.source.local.entity.MovieEntity
import com.android.habibi.core.data.source.remote.response.MovieDetailResponse
import com.android.habibi.core.data.source.remote.response.ResultsItem
import com.android.habibi.core.domain.model.Movie
import com.android.habibi.core.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    fun mapListMovieResponseToDomain(input: List<ResultsItem>): Flow<List<Movie>> {
        val moviesList = ArrayList<Movie>()
        input.map {
            val movie =
                Movie(
                    it.id,
                    it.title,
                    it.overview,
                    it.adult,
                    it.voteCount,
                    it.voteAverage,
                    it.backdropPath,
                    it.posterPath
                )
            moviesList.add(movie)
        }
        return flowOf(moviesList)
    }

    fun mapMovieDetailResponseToDomain(input: MovieDetailResponse): Flow<MovieDetail> {
        val genres = ArrayList<String>()

        input.genres.forEach {
            genres.add(it.name)
        }

        return flowOf(
            MovieDetail(
                input.title,
                input.backdropPath,
                input.id,
                input.voteCount,
                input.overview,
                input.runtime,
                input.posterPath,
                input.voteAverage,
                input.adult,
                genres
            )
        )
    }

    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<Movie> {
        val moviesList = ArrayList<Movie>()
        input.map {
            val movie =
                Movie(
                    it.id,
                    it.title,
                    it.overview,
                    it.adult,
                    it.voteCount,
                    it.voteAverage,
                    it.backdropPath,
                    it.posterPath
                )
            moviesList.add(movie)
        }
        return moviesList
    }

    fun mapMovieDomainToEntities(input: MovieDetail): MovieEntity =
        MovieEntity(
            input.id,
            input.title,
            input.overview,
            input.adult,
            input.voteCount,
            input.voteAverage,
            input.backdropPath,
            input.posterPath
        )
}