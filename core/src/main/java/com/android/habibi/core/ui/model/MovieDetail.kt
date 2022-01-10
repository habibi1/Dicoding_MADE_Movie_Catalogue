package com.android.habibi.core.ui.model

data class MovieDetail(
    val title: String,
    val backdropPath: String,
    val id: Int,
    val voteCount: Int,
    val overview: String,
    val hourRuntime: Int,
    val minuteRuntime: Int,
    val posterPath: String,
    val voteAverage: Float,
    val adult: Boolean,
    val genres: List<String>
)
