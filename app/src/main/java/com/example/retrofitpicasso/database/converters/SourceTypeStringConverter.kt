package com.example.retrofitpicasso.database.converters

import com.example.retrofitpicasso.retrofit.SourceType

class SourceTypeStringConverter {

    fun fromSourceTypeToString(sourceType: SourceType): String {
        return sourceType.toString()
    }

    fun fromStringToSourceType(sourceType: String): SourceType {
        return SourceType.fromString(sourceType)
    }

}