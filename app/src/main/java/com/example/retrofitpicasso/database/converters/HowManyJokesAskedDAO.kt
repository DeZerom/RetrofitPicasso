package com.example.retrofitpicasso.database.converters

import androidx.lifecycle.LiveData
import androidx.room.Query
import androidx.room.Update
import com.example.retrofitpicasso.database.HowManyJokesAsked

interface HowManyJokesAskedDAO {

    @Update
    fun updateCount(howManyJokesAsked: HowManyJokesAsked)

    @Query("SELECT * FROM HowManyJokesAsked LIMIT 1")
    fun getCount(): LiveData<HowManyJokesAsked>

}