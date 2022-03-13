package com.example.retrofitpicasso.retrofit

enum class SourceType {
    GEEK, DAD, SETUP;

    override fun toString(): String {
        return when (this) {
            GEEK -> "GEEK"
            DAD -> "DAD"
            SETUP -> "SETUP"
        }
    }
}