package com.example.retrofitpicasso.database.counter

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.retrofitpicasso.database.counter.HowManyJokesAsked

@Dao
interface HowManyJokesAskedDAO {

    @Update
    fun updateCount(howManyJokesAsked: HowManyJokesAsked)

    @Query("SELECT * FROM HowManyJokesAsked LIMIT 1")
    fun getCount(): LiveData<HowManyJokesAsked>

}