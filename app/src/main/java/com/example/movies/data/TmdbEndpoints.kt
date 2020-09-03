package com.example.movies.data

import com.example.movies.R
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbEndpoints {

    val API_KEY: String
        get() = R.string.api_key.toString()

    @GET("/3/movie/popular")
    fun getMovies(@Query("api_key") key: String = API_KEY, @Query("page") page: Int): Call<PopularMovies>

    @GET("/3/movie/{movie_id}")
    fun getMovieById( @Path("movie_id") id: Int,@Query("api_key") key: String): Call<DetailsResult>

}