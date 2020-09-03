package com.example.movies.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel : ViewModel() {
    private val _det= MutableLiveData<DetailsResult>()
    val movDetails: LiveData<DetailsResult> = _det

    fun callData(id: Int) {

        val request = RetrofitService.buildService(TmdbEndpoints::class.java)
        val call = request.getMovieById(id,"89d5dcd532f98d4b9ebf105e03d875a0" )

        call.enqueue(object : Callback<DetailsResult> {
            override fun onResponse(call: Call<DetailsResult>, response: Response<DetailsResult>) {
                if (response.isSuccessful){
                    _det.value = response.body()
                }
            }
            override fun onFailure(call: Call<DetailsResult>, t: Throwable) {

            }
        })
    }
}