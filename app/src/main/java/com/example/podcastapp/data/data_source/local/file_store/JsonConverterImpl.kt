package com.example.podcastapp.data.data_source.local.file_store

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class JsonConverterImpl(
    private val gson: Gson
): JsonConverter {

    override fun <T> toJson(data: T): String {
        return gson.toJson(data)
    }

    override fun <T> fromJson(data: String, typeToken: TypeToken<T>): T {
        return gson.fromJson(data, typeToken)
    }
}