package com.pajri.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _data = MutableLiveData<List<ItemsItem>>()
    val data: LiveData<List<ItemsItem>> = _data

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
        private const val USERNAME = "a"
    }

    init {
        findUsers(USERNAME)
    }

    private fun findUsers(USERNAME: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(USERNAME)
        client.enqueue(object: Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ){
                _isLoading.value = false
                if(response.isSuccessful){
                    _data.value = response.body()?.items
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


}