package com.example.retrofitpicasso.retrofit

import com.example.retrofitpicasso.retrofit.dads.DadJokeService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Network {
    companion object {
        private val dadRetrofit = Retrofit.Builder().baseUrl("https://icanhazdadjoke.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val dadJokeService by lazy { dadRetrofit.create(DadJokeService::class.java) }


        fun getJokeService(): DadJokeService {
            return dadJokeService
        }
    }
}