package com.example.retrofitpicasso.retrofit

import retrofit2.Call

interface JokeService {
    fun getRandomJoke(): Call<NetJoke>
}