package com.example.retrofitpicasso.retrofit

enum class SourceType {
    GEEK, DAD, SETUP, ERROR;

    override fun toString(): String {
        return when (this) {
            GEEK -> "GEEK"
            DAD -> "DAD"
            SETUP -> "SETUP"
            ERROR -> "ERROR_JOKE"
        }
    }
}