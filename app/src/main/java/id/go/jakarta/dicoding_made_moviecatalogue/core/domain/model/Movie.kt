package id.go.jakarta.dicoding_made_moviecatalogue.core.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val adult: Boolean,
    val voteCount: Int,
    val voteAverage: Double,
    val popularity: Double,
    val releaseDate: String,
    val backdropPath: String,
    val posterPath: String,
)