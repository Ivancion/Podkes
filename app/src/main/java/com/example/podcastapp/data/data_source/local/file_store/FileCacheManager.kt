package com.example.podcastapp.data.data_source.local.file_store

import com.google.gson.reflect.TypeToken

interface FileCacheManager<T> {

    fun writeData(data: T)

    fun readData(typeToken: TypeToken<T>): T

    fun isFileExists(): Boolean

    companion object {
        const val GENRES_FILE = "genres.txt"
        const val REGIONS_FILE = "regions.txt"
        const val LANGUAGES_FILE = "regions.txt"
    }
}