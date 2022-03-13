package com.example.retrofitpicasso

import android.app.Application
import androidx.lifecycle.*
import com.example.retrofitpicasso.database.JokeDatabase
import com.example.retrofitpicasso.repository.JokesRepository
import kotlinx.coroutines.launch

class ComposeActivityViewModel(application: Application): AndroidViewModel(application) {

    private val jokesRepository = JokesRepository(JokeDatabase.getInstance(application))

    val activeJoke = jokesRepository.activeJoke

    private val _countOfJokes = MutableLiveData(0)
    val countOfJokes: LiveData<Int>
        get() = _countOfJokes

    init {
        viewModelScope.launch {
            jokesRepository.getAJokeAndCashIt()
        }
    }

    val onNextJokeBtnClick: () -> Unit = {
    }
}
