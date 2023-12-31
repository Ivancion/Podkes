package com.example.podcastapp.data.json_converter

import com.google.gson.reflect.TypeToken

interface JsonConverter {
    fun <T> toJson(data: T): String

    fun <T> fromJson(data: String, typeToken: TypeToken<T>): T
}