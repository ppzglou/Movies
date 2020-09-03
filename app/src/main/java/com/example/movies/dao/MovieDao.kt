package com.example.movies.dao

import androidx.room.*

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    suspend fun getAll(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Query("SELECT * from movie WHERE movie_id = :movieId")
    fun findById(movieId: String):Movie

    @Delete
    suspend fun delete(movie: Movie)
}