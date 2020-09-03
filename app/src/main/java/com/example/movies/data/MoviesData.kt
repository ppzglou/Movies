package com.example.movies.data


data class PopularMovies(
    val results: List<Result>
)

data class Result(
    val id: Int,
    val overview: String,
    val poster_path: String,
    val title: String,
    val vote_average: Double
)


data class DetailsResult(
    val id: Int,
    val adult: Boolean,
    val overview: String,
    val release_date: String,
    val backdrop_path: String,
    val title: String,
    val vote_count: Int,
    val vote_average: Double
)
