package com.example.retrofitpicasso.composeActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitpicasso.retrofit.jokes.Joke
import com.example.retrofitpicasso.retrofit.jokes.Jokes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ComposeActivityViewModel: ViewModel() {

    private val _state = MutableLiveData(MainActivityState("Click on the button to get a joke"))
    val state: LiveData<MainActivityState>
        get() = _state

    private val retrofit = Retrofit.Builder().baseUrl("https://icanhazdadjoke.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val jokeService = retrofit.create(Jokes::class.java)

    val onNextJokeBtnClick: () -> Unit = {
        viewModelScope.launch(Dispatchers.IO) {
            val call = jokeService.getRandomJoke(Jokes.ResponseTypes.JSON.toString())
            val response = call.execute()

            val currentState = _state.value
            if (response.isSuccessful) {
                val joke = response.body() ?: Joke("", "Null joke", 200)
                _state.postValue(currentState?.copy(joke = joke.joke,
                    countOfJokes = currentState.countOfJokes + 1))
            } else {
                val joke = Joke("", "Unsuccessful response", response.code())
                _state.postValue(currentState?.copy(joke = joke.joke,
                    countOfJokes = currentState.countOfJokes))
            }
        }
    }

    data class MainActivityState(
        var joke: String,
        var countOfJokes: Int = 0
    )
}
