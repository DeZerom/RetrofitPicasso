package com.example.retrofitpicasso.retrofit.setup

import com.example.retrofitpicasso.retrofit.NetJoke
import retrofit2.Call
import retrofit2.http.GET

interface SetupJokeService {
    @GET("random_joke")
    fun getRandomJoke(): Call<SetupNetJoke>
}