package com.example.podcastapp.data.json_converter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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