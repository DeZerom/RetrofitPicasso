package com.example.retrofitpicasso

import android.app.Application
import androidx.lifecycle.*
import com.example.retrofitpicasso.database.JokeDatabase
import com.example.retrofitpicasso.repository.HowManyJokesAskedRepository
import com.example.retrofitpicasso.repository.JokesRepository
import com.example.retrofitpicasso.retrofit.Network
import kotlinx.coroutines.launch

class ComposeActivityViewModel(application: Application): AndroidViewModel(application) {

    private val jokesRepository = JokesRepository(JokeDatabase.getInstance(application),
        Network(application))
    private val countRepository = HowManyJokesAskedRepository(JokeDatabase.getInstance(application))

    val activeJoke = jokesRepository.activeJoke
    val count = countRepository.count

    init {
        viewModelScope.launch {
            jokesRepository.getAJokeAndCahIt()
        }
    }

    val onNextJokeBtnClick: () -> Unit = {
        viewModelScope.launch {
            jokesRepository.getAJokeAndCahIt()
            countRepository.increaseCount()
        }
    }
}
