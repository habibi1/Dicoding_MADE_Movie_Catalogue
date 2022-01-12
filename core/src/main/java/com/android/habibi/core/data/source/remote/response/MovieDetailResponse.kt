package com.android.habibi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("backdrop_path")
	val backdropPath: String,

	@field:SerializedName("genres")
	val genres: List<GenresItem>,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("vote_count")
	val voteCount: Int,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("runtime")
	val runtime: Int,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("adult")
	val adult: Boolean
)

data class GenresItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
