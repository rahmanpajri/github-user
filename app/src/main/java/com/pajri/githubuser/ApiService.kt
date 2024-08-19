package com.pajri.githubuser

import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") username: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetail(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}