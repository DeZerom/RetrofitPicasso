package com.example.retrofitpicasso.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.retrofitpicasso.database.counter.HowManyJokesAsked
import com.example.retrofitpicasso.database.JokeDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HowManyJokesAskedRepository(database: JokeDatabase) {
    private val howManyJokesAskedDao = database.howManyJokesAskedDao

    private val _count = howManyJokesAskedDao.getCount()
    val count: LiveData<Int>
        get() = Transformations.map(_count) { it.count }


    suspend fun increaseCount() {
        val count = _count.value ?: HowManyJokesAsked(0, 0)
        withContext(Dispatchers.IO) {
            howManyJokesAskedDao.updateCount(count
                .copy(count = count.count + 1))
        }
    }

}