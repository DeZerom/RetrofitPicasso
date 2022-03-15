package com.example.retrofitpicasso.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseJoke::class], version = 1, exportSchema = false)
abstract class JokeDatabase: RoomDatabase() {
    abstract val jokeDao: JokeDAO

    companion object {
        @Volatile private lateinit var instance: JokeDatabase

        @Synchronized
        fun getInstance(context: Context): JokeDatabase {
                if (!::instance.isInitialized) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        JokeDatabase::class.java,
                        "Jokes"
                    ).build()
                }
                return instance
        }
    }
}