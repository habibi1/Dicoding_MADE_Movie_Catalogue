package id.go.jakarta.dicoding_made_moviecatalogue.core.utils

import id.go.jakarta.dicoding_made_moviecatalogue.core.data.source.local.entity.MovieEntity
import id.go.jakarta.dicoding_made_moviecatalogue.core.data.source.remote.response.ResultsItem
import id.go.jakarta.dicoding_made_moviecatalogue.core.domain.model.Movie

object DataMapper {
    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
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
        }

    fun mapResponseToEntities(input: List<ResultsItem>): List<MovieEntity> {
        val moviesList = ArrayList<MovieEntity>()
        input.map {
            val movie =
                MovieEntity(
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

    fun mapDomainToEntity(input: Movie): MovieEntity =
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