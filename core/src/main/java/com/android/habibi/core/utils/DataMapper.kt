package com.android.habibi.core.utils

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
                    it.popularity,
                    it.releaseDate,
                    it.backdropPath,
                    it.posterPath
                )
            moviesList.add(movie)
        }
        return flowOf(moviesList)
    }

    fun mapMovieDetailResponseToDomain(input: MovieDetailResponse): Flow<MovieDetail> =
        flowOf(
            MovieDetail(
            input.originalLanguage,
            input.imdbId,
            input.video,
            input.title,
            input.backdropPath,
            input.revenue,
            input.popularity,
            input.id,
            input.voteCount,
            input.budget,
            input.overview,
            input.originalTitle,
            input.runtime,
            input.posterPath,
            input.releaseDate,
            input.voteAverage,
            input.tagline,
            input.adult,
            input.homepage,
            input.status
            )
        )

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
                    it.popularity,
                    it.releaseDate,
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
            input.popularity,
            input.releaseDate,
            input.backdropPath,
            input.posterPath
        )
}