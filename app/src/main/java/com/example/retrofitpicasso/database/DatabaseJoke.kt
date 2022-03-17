package com.example.retrofitpicasso.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.retrofitpicasso.retrofit.SourceType

@Entity
data class DatabaseJoke(
    @PrimaryKey val id: Long,
    val joke: String,
    val source: SourceType
) {}