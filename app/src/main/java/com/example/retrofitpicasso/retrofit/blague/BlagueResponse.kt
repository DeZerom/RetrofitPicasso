package com.example.retrofitpicasso.retrofit.blague

import com.example.retrofitpicasso.database.jokes.DatabaseJoke
import com.example.retrofitpicasso.domain_joke.Joke
import com.example.retrofitpicasso.retrofit.NetJoke

data class BlagueResponse(
    var status: Int,
    var response: String,
    var error: Boolean,
    var joke: BlagueNetJoke
): NetJoke {
    override fun toDomainJoke(): Joke {
        return joke.toDomainJoke()
    }

    override fun toDatabaseJoke(): DatabaseJoke {
        return joke.toDatabaseJoke()
    }
}