package com.example.retrofitpicasso.retrofit.jokes

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface Jokes {

    @GET("/")
    fun getRandomJoke(@Header("Accept") responseType: String): Call<Joke>

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