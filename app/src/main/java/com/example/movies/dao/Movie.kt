package com.example.movies.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "movie_id") val movieId: String,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "vote_average") val voteAverage: String?,
    @ColumnInfo(name = "posterPath") val posterPath: String?
)