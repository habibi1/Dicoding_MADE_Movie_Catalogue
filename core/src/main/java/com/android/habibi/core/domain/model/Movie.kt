package com.android.habibi.core.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val posterPath: String
)