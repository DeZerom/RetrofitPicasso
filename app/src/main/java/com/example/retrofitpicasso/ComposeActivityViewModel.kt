package com.example.retrofitpicasso

import android.app.Application
import androidx.lifecycle.*
import com.example.retrofitpicasso.database.JokeDatabase
import com.example.retrofitpicasso.repository.HowManyJokesAskedRepository
import com.example.retrofitpicasso.repository.JokesRepository
import com.example.retrofitpicasso.retrofit.Network
import com.example.retrofitpicasso.retrofit.SourceType
import kotlinx.coroutines.launch

class ComposeActivityViewModel(application: Application): AndroidViewModel(application) {

    private val jokesRepository = JokesRepository(JokeDatabase.getInstance(application),
        Network(application))
    private val countRepository = HowManyJokesAskedRepository(JokeDatabase.getInstance(application))

    val activeSources = jokesRepository.affirmedSources

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
        jokesRepository.onAffirmedSourceTypesChanged(SourceType.GEEK, it)
    }

    val onDadSourceChkBoxClicked: (Boolean) -> Unit = {
        jokesRepository.onAffirmedSourceTypesChanged(SourceType.DAD, it)
    }

    val onSetupSourceChkBoxClicked: (Boolean) -> Unit = {
        jokesRepository.onAffirmedSourceTypesChanged(SourceType.SETUP, it)
    }

    val onBlagueSourceChkBoxClicked: (Boolean) -> Unit = {
        jokesRepository.onAffirmedSourceTypesChanged(SourceType.BLAGUE, it)
    }

    data class AffirmedSources(
        val geek: Boolean = true,
        val dad: Boolean = true,
        val blague: Boolean = true,
        val setup: Boolean = true
    )
}
