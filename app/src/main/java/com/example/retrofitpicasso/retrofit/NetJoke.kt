package com.example.retrofitpicasso.retrofit

import com.example.retrofitpicasso.database.jokes.DatabaseJoke
import com.example.retrofitpicasso.domain_joke.Joke

interface NetJoke {
    fun toDomainJoke(): Joke
    fun toDatabaseJoke(): DatabaseJoke
}