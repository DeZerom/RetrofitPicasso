package com.example.retrofitpicasso.retrofit.blague

import com.example.retrofitpicasso.database.jokes.DatabaseJoke
import com.example.retrofitpicasso.domain_joke.Joke
import com.example.retrofitpicasso.retrofit.NetJoke
import com.example.retrofitpicasso.retrofit.SourceType

data class BlagueNetJoke(
    var question : String,
    var answer   : String,
    var id       : Int
): NetJoke {

    private fun getSingleJokeFromQuestionAndAnswer(): String {
        return "$question $answer"
    }

    override fun toDomainJoke(): Joke {
        return Joke(getSingleJokeFromQuestionAndAnswer())
    }

    override fun toDatabaseJoke(): DatabaseJoke {
        return DatabaseJoke(id.toLong(), getSingleJokeFromQuestionAndAnswer(), SourceType.BLAGUE)
    }
}