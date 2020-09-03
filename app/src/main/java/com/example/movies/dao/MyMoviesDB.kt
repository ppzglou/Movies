package com.example.movies.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1)
abstract class MyMoviesDB : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}