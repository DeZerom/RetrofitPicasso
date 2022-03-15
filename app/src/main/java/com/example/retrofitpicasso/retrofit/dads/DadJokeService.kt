package com.example.retrofitpicasso.retrofit.dads

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface DadJokeService {

    @GET("/")
    fun getRandomJoke(@Header("Accept") responseType: String): Call<DadNetJoke>

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