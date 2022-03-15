package com.example.retrofitpicasso.retrofit.dads

import androidx.lifecycle.Transformations.map
import com.example.retrofitpicasso.database.DatabaseJoke
import com.example.retrofitpicasso.domain_joke.Joke
import com.example.retrofitpicasso.retrofit.NetJoke
import com.example.retrofitpicasso.retrofit.SourceType
import kotlin.math.absoluteValue

data class DadNetJoke(
    val id: String,
    val joke: String,
    val status: Int
): NetJoke
{
    override fun toDomainJoke(): Joke {
        return Joke(joke)
    }

    override fun toDatabaseJoke(): DatabaseJoke {
        return DatabaseJoke((id.hashCode().toLong().absoluteValue * 10), joke, SourceType.DAD)
    }
}