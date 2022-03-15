package com.example.retrofitpicasso.retrofit.geeks

import retrofit2.Call
import retrofit2.http.GET

interface GeekJokeService {
    @GET("api?format=json")
    fun getRandomJoke(): Call<GeekNetJoke>
}