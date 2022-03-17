package com.example.retrofitpicasso.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HowManyJokesAsked(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val count: Int
) {}