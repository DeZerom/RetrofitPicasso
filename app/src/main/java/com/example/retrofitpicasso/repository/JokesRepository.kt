package com.example.retrofitpicasso.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.retrofitpicasso.database.JokeDatabase
import com.example.retrofitpicasso.domain_joke.Joke
import com.example.retrofitpicasso.retrofit.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class JokesRepository(database: JokeDatabase) {

    private var jokeService = Network.getJokeService()

    private val jokeDao = database.jokeDao

    private val _activeJoke = MutableLiveData<Joke>()
    val activeJoke: LiveData<Joke>
        get() = _activeJoke

    /**
     * Gets joke and cashes it. A new one joke will be written in [activeJoke]
     */
    suspend fun getAJokeAndCashIt() {
        withContext(Dispatchers.IO) {
            val joke = jokeService.getRandomJoke().await()
            _activeJoke.postValue(joke.toDomainJoke())
            jokeDao.insertJoke(joke.toDatabaseJoke())
        }
    }

}