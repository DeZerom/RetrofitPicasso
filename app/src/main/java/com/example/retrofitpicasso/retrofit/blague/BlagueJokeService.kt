package com.example.retrofitpicasso.retrofit.blague

import retrofit2.Call
import retrofit2.http.GET

interface BlagueJokeService {
    @GET("random?lang=en")
    fun getRandomJoke(): Call<BlagueResponse>
}