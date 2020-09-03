package com.example.movies

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.movies.dao.MyMoviesDB
import kotlinx.coroutines.launch


class DashBoardActivity : AppCompatActivity() {
    private val WRITE_STORAGE_CODE = 101
    private val READ_STORAGE_CODE = 101

    lateinit var db: MyMoviesDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            WRITE_STORAGE_CODE
        )
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            READ_STORAGE_CODE
        )


        db = Room.databaseBuilder(
            applicationContext,
            MyMoviesDB::class.java, "movie"
        ).build()

    }
}