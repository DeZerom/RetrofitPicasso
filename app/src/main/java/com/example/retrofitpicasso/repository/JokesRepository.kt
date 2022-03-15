package com.example.retrofitpicasso.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.retrofitpicasso.database.JokeDatabase
import com.example.retrofitpicasso.domain_joke.Joke
import com.example.retrofitpicasso.retrofit.ErrorJoke
import com.example.retrofitpicasso.retrofit.NetJoke
import com.example.retrofitpicasso.retrofit.Network
import com.example.retrofitpicasso.retrofit.SourceType
import com.example.retrofitpicasso.retrofit.dads.DadJokeService
import com.example.retrofitpicasso.retrofit.dads.DadNetJoke
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await
import java.lang.Exception

class JokesRepository(database: JokeDatabase, private val network: Network) {

    private val jokeDao = database.jokeDao

    private val _activeJoke = MutableLiveData<Joke>()
    val activeJoke: LiveData<Joke>
        get() = _activeJoke

    /**
     * Gets joke and cashes it. A new one joke will be written in [activeJoke]
     */
    suspend fun getAJokeAndCashIt() {
        withContext(Dispatchers.IO) {
            val result = network.getJokeFrom(SourceType.GEEK).runCatching {
                await()
            }

            val joke = result.getOrElse {
                Log.e("JokesRepository", it?.message ?: "")
                ErrorJoke(it.toString())
            }
            _activeJoke.postValue(joke.toDomainJoke())

            val dbJoke = joke.toDatabaseJoke()
            if (dbJoke.source != SourceType.ERROR) jokeDao.insertJoke(dbJoke)
        }
    }

}