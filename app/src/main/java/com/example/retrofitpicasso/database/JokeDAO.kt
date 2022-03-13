package com.example.retrofitpicasso.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query

@Dao
interface JokeDAO {

    @Query("SELECT * FROM DatabaseJoke WHERE id = :id")
    fun selectById(id: Int): DatabaseJoke

    @Insert(onConflict = IGNORE)
    fun insertJoke(joke: DatabaseJoke)

}