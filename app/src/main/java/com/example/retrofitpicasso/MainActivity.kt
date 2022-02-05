package com.example.retrofitpicasso

import android.icu.number.NumberFormatter.with
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.retrofitpicasso.retrofit.jokes.Joke
import com.example.retrofitpicasso.retrofit.jokes.Jokes
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)

        val retrofit = Retrofit.Builder().baseUrl("https://icanhazdadjoke.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val jokeService = retrofit.create(Jokes::class.java)

        button.setOnClickListener {
            val call = jokeService.getRandomJoke(Jokes.ResponseTypes.JSON.toString())
            call.enqueue(enqueueCallback)
        }
    }

    private val enqueueCallback = object : Callback<Joke> {
        override fun onResponse(call: Call<Joke>, response: Response<Joke>) {
            insertStatusCat(response.code())
            if (!response.isSuccessful) Log.e("1234", response.code().toString())

            val view = findViewById<TextView>(R.id.textView)

            val joke = response.body() ?: Joke("", "null joke", 204)
            view.text = joke.joke
        }

        override fun onFailure(call: Call<Joke>, t: Throwable) {
            val view = findViewById<TextView>(R.id.textView)

            val msg = t.localizedMessage
            msg?.let {
                view.text = it
            } ?: run {
                view.text = "null error msg"
            }
        }
    }

    private fun insertStatusCat(code: Int) {
        val imageView = findViewById<ImageView>(R.id.imageView)
        Picasso.get().load("https://http.cat/$code").into(imageView)
    }
}