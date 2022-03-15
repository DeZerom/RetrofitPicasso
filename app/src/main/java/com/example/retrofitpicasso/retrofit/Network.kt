package com.example.retrofitpicasso.retrofit

import android.app.Application
import com.example.retrofitpicasso.retrofit.SourceType.*
import com.example.retrofitpicasso.retrofit.dads.DadJokeService
import com.example.retrofitpicasso.retrofit.geeks.GeekJokeService
import com.example.retrofitpicasso.retrofit.setup.SetupJokeService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalArgumentException

class Network(application: Application) {
    private val dadRetrofit: Retrofit
    private val geekRetrofit: Retrofit
    private val setupRetrofit: Retrofit

    init {
        val interceptor = ConnectivityInterceptor(application)

        dadRetrofit = Retrofit.Builder().baseUrl("https://icanhazdadjoke.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(interceptor).build())
            .build()

        geekRetrofit = Retrofit.Builder()
            .baseUrl("https://geek-jokes.sameerkumar.website/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(interceptor).build())
            .build()

        setupRetrofit = Retrofit.Builder()
            .baseUrl("https://official-joke-api.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(interceptor).build())
            .build()
    }

    private val dadJokeService by lazy { dadRetrofit.create(DadJokeService::class.java) }
    private val geekJokeService by lazy { geekRetrofit.create(GeekJokeService::class.java) }
    private val setupJokeService by lazy { setupRetrofit.create(SetupJokeService::class.java)}

    fun getJokeFrom(source: SourceType): Call<NetJoke> {
        return when (source) {
            GEEK -> getGeekJokeCall()
            DAD -> getDadJokeCall()
            SETUP -> getSetupJokeCall()
            ERROR -> throw IllegalArgumentException("Cannot get an ErrorJoke")
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getDadJokeCall(): Call<NetJoke> {
        return dadJokeService
            .getRandomJoke(DadJokeService.ResponseTypes.JSON.toString()) as Call<NetJoke>
    }

    @Suppress("UNCHECKED_CAST")
    private fun getGeekJokeCall(): Call<NetJoke> {
        return geekJokeService.getRandomJoke() as Call<NetJoke>
    }

    @Suppress("UNCHECKED_CAST")
    private fun getSetupJokeCall(): Call<NetJoke> {
        return setupJokeService.getRandomJoke() as Call<NetJoke>
    }
}