package com.example.retrofitpicasso.retrofit.setup

import com.example.retrofitpicasso.database.DatabaseJoke
import com.example.retrofitpicasso.domain_joke.Joke
import com.example.retrofitpicasso.retrofit.NetJoke
import com.example.retrofitpicasso.retrofit.SourceType
import kotlin.math.absoluteValue

data class SetupNetJoke(
    val setup: String,
    val punchline: String
): NetJoke {

    private fun convertSetupPunchlineToSingleJoke(): String {
        return "$setup $punchline"
    }

    override fun toDomainJoke(): Joke {
        return Joke(convertSetupPunchlineToSingleJoke())
    }

    override fun toDatabaseJoke(): DatabaseJoke {
        val joke = convertSetupPunchlineToSingleJoke()
        return DatabaseJoke(joke.hashCode().toLong().absoluteValue * 10 + 2, joke, SourceType.SETUP)
    }
}