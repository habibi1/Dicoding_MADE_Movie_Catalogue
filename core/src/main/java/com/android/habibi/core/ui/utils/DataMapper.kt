package com.android.habibi.core.ui.utils

import com.android.habibi.core.ui.model.Movie as MoviePresentation
import com.android.habibi.core.domain.model.Movie as MovieDomain
import com.android.habibi.core.ui.model.MovieDetail as MovieDetailPresentation
import com.android.habibi.core.domain.model.MovieDetail as MovieDetailDomain

object DataMapper {
    fun mapMovieDomainToPresentation(input: List<MovieDomain>): List<MoviePresentation> {
        val result = ArrayList<MoviePresentation>()
        input.map {
            result.add(
                MoviePresentation(
                    it.id,
                    it.title,
                    (it.voteAverage/2).toFloat(),
                    it.posterPath
                )
            )
        }
        return result
    }

    fun mapMovieDetailToPresentation(input: MovieDetailDomain): MovieDetailPresentation{
        return MovieDetailPresentation(
            input.title,
            input.backdropPath,
            input.id,
            input.voteCount,
            input.overview,
            input.runtime/60,
            input.runtime%60,
            input.posterPath,
            input.voteAverage.toFloat(),
            input.adult,
            input.genres
        )
    }

    fun mapMoviePresentationToDomain(input: MovieDetailPresentation): MovieDetailDomain{
        return MovieDetailDomain(
            input.title,
            input.backdropPath,
            input.id,
            input.voteCount,
            input.overview,
            input.hourRuntime*60 + input.minuteRuntime,
            input.posterPath,
            input.voteAverage.toDouble(),
            input.adult,
            input.genres
        )
    }
}