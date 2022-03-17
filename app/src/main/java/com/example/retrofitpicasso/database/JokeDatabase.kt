package com.example.retrofitpicasso.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [DatabaseJoke::class, HowManyJokesAsked::class],
    version = 2, exportSchema = false)
abstract class JokeDatabase: RoomDatabase() {
    abstract val jokeDao: JokeDAO

    companion object {
        @Volatile private lateinit var instance: JokeDatabase

        private val migration_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS HowManyJokesAsked(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, count INTEGER NOT NULL)")
                database.execSQL("INSERT INTO HowManyJokesAsked(count) VALUES(0)")
            }
        }

        @Synchronized
        fun getInstance(context: Context): JokeDatabase {
                if (!::instance.isInitialized) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        JokeDatabase::class.java,
                        "Jokes"
                    ).addMigrations(migration_1_2).build()
                }
                return instance
        }
    }
}