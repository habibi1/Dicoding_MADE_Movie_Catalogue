package com.android.habibi.core.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val adult: Boolean,
    val voteCount: Int,
    val voteAverage: Double,
    val backdropPath: String,
    val posterPath: String
)