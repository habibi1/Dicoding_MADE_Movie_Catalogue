package com.android.habibi.core.domain.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.android.habibi.core.domain.model.MovieDetail as MovieDetailDomain
//import com.android.habibi.core.ui.model.MovieDetail as MovieDetailPresentation

object DataMapper {
/*    fun mapMovieDetailDomainToPresentation(input: MovieDetailDomain): Flow<MovieDetailPresentation> {
        val runtimeHour = input.runtime/60
        val runtimeMinute = input.runtime%60

        return flowOf(
            MovieDetailPresentation(
                input.title,
                input.backdropPath,
                input.id,
                input.voteCount.toString(),
                input.overview,
                runtimeHour,
                runtimeMinute,
                input.posterPath,
                input.voteAverage.toString(),
                input.adult,
                input.genres
            )
        )
    }*/
}