package com.example.retrofitpicasso.retrofit

import com.example.retrofitpicasso.database.DatabaseJoke
import com.example.retrofitpicasso.domain_joke.Joke

data class ErrorJoke(
    val errorMsg: String
): NetJoke {
    override fun toDomainJoke(): Joke {
        return Joke(errorMsg)
    }

    override fun toDatabaseJoke(): DatabaseJoke {
        return DatabaseJoke(-1, errorMsg, SourceType.ERROR)
    }
}