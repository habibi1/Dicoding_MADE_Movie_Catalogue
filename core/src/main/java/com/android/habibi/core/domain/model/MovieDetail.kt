package com.android.habibi.core.domain.model

data class MovieDetail(
    val title: String,
    val backdropPath: String,
    val id: Int,
    val voteCount: Int,
    val overview: String,
    val runtime: Int,
    val posterPath: String,
    val voteAverage: Double,
    val adult: Boolean,
    val genres: List<String>
)
