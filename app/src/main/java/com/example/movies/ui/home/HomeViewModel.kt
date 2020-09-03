package com.example.movies.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies.data.PopularMovies
import com.example.movies.data.Result
import com.example.movies.data.RetrofitService
import com.example.movies.data.TmdbEndpoints
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _rspns = MutableLiveData<List<Result>>()
    val response: LiveData<List<Result>> = _rspns
    private var page = 1

    fun callData() {

        val request = RetrofitService.buildService(TmdbEndpoints::class.java)
        val call = request.getMovies("89d5dcd532f98d4b9ebf105e03d875a0", page)
        if (page == 501) return
        page++

        call.enqueue(object : Callback<PopularMovies> {
            override fun onResponse(call: Call<PopularMovies>, response: Response<PopularMovies>) {
                if (response.isSuccessful){
                    _rspns.value = response.body()!!.results
                }
            }
            override fun onFailure(call: Call<PopularMovies>, t: Throwable) {
            }
        })
    }

}

