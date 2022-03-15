package com.example.retrofitpicasso.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.retrofitpicasso.database.DatabaseJoke
import com.example.retrofitpicasso.database.JokeDatabase
import com.example.retrofitpicasso.domain_joke.Joke
import com.example.retrofitpicasso.retrofit.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class JokesRepository(database: JokeDatabase, private val network: Network) {

    private val jokeDao = database.jokeDao

    private val _activeJoke = MutableLiveData<Joke>()
    val activeJoke: LiveData<Joke>
        get() = _activeJoke

    private var activeJokeSource = SourceType.GEEK

    /**
     * Gets joke and cashes it. A new one joke will be written in [activeJoke]
     */
    suspend fun getAJokeAndCahIt() {
        withContext(Dispatchers.IO) {
            val joke = getAJokeFromNet()
            _activeJoke.postValue(joke)
        }
    }

    private suspend fun getAJokeFromNet(): Joke {
        return try {
            val joke = network.getJokeFrom(activeJokeSource).await()
            cashAJoke(joke.toDatabaseJoke())
            joke.toDomainJoke()
        } catch(e: NoConnectionException) {
            getAJokeFromDatabase()
        } catch(e: Exception) {
            ErrorJoke(e.toString()).toDomainJoke()
        } finally {
            activeJokeSource = activeJokeSource.next()
        }
    }

    private suspend fun getAJokeFromDatabase(): Joke {
        return withContext(Dispatchers.IO) {
            Joke("Cash loading is not implemented yet")
        }
    }

    private suspend fun cashAJoke(joke: DatabaseJoke) {
        withContext(Dispatchers.IO) {
            jokeDao.insertJoke(joke)
        }
    }

}