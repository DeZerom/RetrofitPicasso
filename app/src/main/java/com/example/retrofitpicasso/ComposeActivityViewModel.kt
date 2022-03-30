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

    private val _activeSources = MutableLiveData<ActiveSources>(ActiveSources())
    val activeSources: LiveData<ActiveSources>
        get() = _activeSources

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

    val onGeekSourceChkBoxClicked: (Boolean) -> Unit = {
        _activeSources.value = _activeSources.value?.copy(geek = it)
    }

    val onDadSourceChkBoxClicked: (Boolean) -> Unit = {
        _activeSources.value = _activeSources.value?.copy(dad = it)
    }

    val onSetupSourceChkBoxClicked: (Boolean) -> Unit = {
        _activeSources.value = _activeSources.value?.copy(setup = it)
    }

    val onBlagueSourceChkBoxClicked: (Boolean) -> Unit = {
        _activeSources.value = _activeSources.value?.copy(blague = it)
    }

    data class ActiveSources(
        val geek: Boolean = true,
        val dad: Boolean = true,
        val blague: Boolean = true,
        val setup: Boolean = true
    )
}
