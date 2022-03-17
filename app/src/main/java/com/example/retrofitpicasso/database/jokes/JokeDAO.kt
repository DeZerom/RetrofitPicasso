package com.example.retrofitpicasso.database.jokes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.example.retrofitpicasso.retrofit.SourceType

@Dao
interface JokeDAO {

    @Query("SELECT * FROM DatabaseJoke WHERE id = :id")
    fun selectById(id: Int): DatabaseJoke

    @Query("SELECT * FROM DatabaseJoke WHERE source = :sourceType")
    fun selectBySourceType(sourceType: SourceType): List<DatabaseJoke>

    @Insert(onConflict = IGNORE)
    fun insertJoke(joke: DatabaseJoke)

}