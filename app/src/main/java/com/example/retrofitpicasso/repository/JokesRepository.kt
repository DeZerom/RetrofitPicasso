package com.example.retrofitpicasso.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.retrofitpicasso.ComposeActivityViewModel
import com.example.retrofitpicasso.database.jokes.DatabaseJoke
import com.example.retrofitpicasso.database.JokeDatabase
import com.example.retrofitpicasso.domain_joke.Joke
import com.example.retrofitpicasso.retrofit.*
import com.example.retrofitpicasso.retrofit.SourceType.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await
import java.util.*

class JokesRepository(database: JokeDatabase, private val network: Network) {

    private val jokeDao = database.jokeDao

    private val _activeJoke = MutableLiveData<Joke>()

    /**
     * Contains actual [Joke]
     */
    val activeJoke: LiveData<Joke>
        get() = _activeJoke

    private var activeJokeSource = GEEK

    private val _affirmedSources = MutableLiveData(
        ComposeActivityViewModel.AffirmedSources()
    )

    /**
     * Contains affirmed joke sources
     */
    val affirmedSources: LiveData<ComposeActivityViewModel.AffirmedSources>
        get() = _affirmedSources

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
            activeJokeSource = nextJokeType()
        }
    }

    private suspend fun getAJokeFromDatabase(): Joke {
        val random = Random()
        return withContext(Dispatchers.IO) {
            val listOfJokes = jokeDao.selectBySourceType(activeJokeSource)
            Joke(listOfJokes[random.nextInt(listOfJokes.size)].joke)
        }
    }

    private suspend fun cashAJoke(joke: DatabaseJoke) {
        withContext(Dispatchers.IO) {
            jokeDao.insertJoke(joke)
        }
    }

    private fun nextJokeType(): SourceType {
        val affirmed = _affirmedSources.value ?: ComposeActivityViewModel.AffirmedSources()

        if (!affirmed.geek && !affirmed.dad && !affirmed.setup && !affirmed.blague)
            return ERROR

        var isFound = false
        var source = activeJokeSource
        while(!isFound) {
            source = source.next()
            when (source) {
                GEEK -> { if (affirmed.geek) isFound = true }
                DAD -> { if (affirmed.dad) isFound = true }
                SETUP -> { if (affirmed.setup) isFound = true }
                BLAGUE -> { if (affirmed.blague) isFound = true }
                ERROR -> {}
            }
        }

        return source
    }

    private fun checkActiveSourceAndChangeIfNeeded() {
        val affirmed = _affirmedSources.value ?: ComposeActivityViewModel.AffirmedSources()
        var changeNeeded = false
        when(activeJokeSource) {
            GEEK -> if (!affirmed.geek) changeNeeded = true
            DAD -> if (!affirmed.dad) changeNeeded = true
            SETUP -> if (!affirmed.setup) changeNeeded = true
            ERROR -> changeNeeded = true
            BLAGUE -> if (!affirmed.blague) changeNeeded = true
        }

        if (changeNeeded) activeJokeSource = nextJokeType()
    }


    /**
     * Changes [affirmedSources] according to the parameters.
     */
    fun onAffirmedSourceTypesChanged(sourceType: SourceType, isAffirmed: Boolean) {
        val currentSources = _affirmedSources.value ?: ComposeActivityViewModel.AffirmedSources()
        _affirmedSources.value = when(sourceType) {
            GEEK -> currentSources.copy(geek = isAffirmed)
            DAD -> currentSources.copy(dad = isAffirmed)
            SETUP -> currentSources.copy(setup = isAffirmed)
            ERROR -> currentSources
            BLAGUE -> currentSources.copy(blague = isAffirmed)
        }
        checkActiveSourceAndChangeIfNeeded()
    }

}