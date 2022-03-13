package com.example.retrofitpicasso.retrofit.dads

import com.example.retrofitpicasso.retrofit.JokeService
import com.example.retrofitpicasso.retrofit.NetJoke
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

abstract class DadJokeService: JokeService {

    override fun getRandomJoke(): Call<NetJoke> {
        @Suppress("UNCHECKED_CAST")
        return getRandomJoke(ResponseTypes.JSON.toString()) as Call<NetJoke>
    }

    @GET("/")
    protected abstract fun getRandomJoke(@Header("Accept") responseType: String): Call<DadNetJoke>

    enum class ResponseTypes {
        JSON, PLAIN_TEXT;

        override fun toString(): String {
            return when(this) {
                JSON -> "application/json"
                PLAIN_TEXT -> "text/plain"
            }
        }
    }

}