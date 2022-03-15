package com.example.retrofitpicasso.retrofit.geeks

import com.example.retrofitpicasso.database.DatabaseJoke
import com.example.retrofitpicasso.domain_joke.Joke
import com.example.retrofitpicasso.retrofit.NetJoke
import com.example.retrofitpicasso.retrofit.SourceType

data class GeekNetJoke(
    val joke: String
): NetJoke {
    override fun toDomainJoke(): Joke {
        return Joke(joke)
    }

    override fun toDatabaseJoke(): DatabaseJoke {
        return DatabaseJoke(joke.hashCode() * 10 + 1, joke, SourceType.GEEK)
    }
}