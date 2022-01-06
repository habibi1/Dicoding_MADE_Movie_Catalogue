package com.android.habibi.core.domain.model

data class MovieDetail(
    val originalLanguage: String,
    val imdbId: String,
    val video: Boolean,
    val title: String,
    val backdropPath: String,
    val revenue: Int,
    val popularity: Double,
    val id: Int,
    val voteCount: Int,
    val budget: Int,
    val overview: String,
    val originalTitle: String,
    val runtime: Int,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val tagline: String,
    val adult: Boolean,
    val homepage: String,
    val status: String
)
