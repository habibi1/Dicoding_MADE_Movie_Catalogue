package com.android.habibi.core.ui.model

data class MovieDetailPresentation(
    val title: String,
    val backdropPath: String,
    val id: Int,
    val voteCount: String,
    val overview: String,
    val runtimeHour: Int,
    val runtimeMinute: Int,
    val posterPath: String,
    val voteAverage: String,
    val adult: Boolean,
    val genres: List<String>
)
