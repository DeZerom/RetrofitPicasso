package com.example.retrofitpicasso.database.counter

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HowManyJokesAsked(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val count: Int
) {}