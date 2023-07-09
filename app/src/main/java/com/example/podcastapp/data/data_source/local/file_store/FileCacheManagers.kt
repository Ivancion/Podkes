package com.example.podcastapp.data.data_source.local.file_store

import android.content.Context
import com.example.podcastapp.data.data_source.remote.dto.GenresResponse
import com.example.podcastapp.data.data_source.remote.dto.LanguagesResponse
import com.example.podcastapp.data.data_source.remote.dto.RegionsResponse

data class FileCacheManagers(
    val genresFileCacheManager: GenresFileCacheManager,
    val regionsFileCacheManager: RegionsFileCacheManager,
    val languagesFileCacheManager: LanguagesFileCacheManager
)

class GenresFileCacheManager(
    context: Context,
    jsonConverter: JsonConverter
): FileCacheManagerImpl<GenresResponse>(context, jsonConverter) {
    override val fileName: String
        get() = FileCacheManager.GENRES_FILE
}

class RegionsFileCacheManager(
    context: Context,
    jsonConverter: JsonConverter
): FileCacheManagerImpl<RegionsResponse>(context, jsonConverter) {
    override val fileName: String
        get() = FileCacheManager.REGIONS_FILE
}

class LanguagesFileCacheManager(
    context: Context,
    jsonConverter: JsonConverter
): FileCacheManagerImpl<LanguagesResponse>(context, jsonConverter) {
    override val fileName: String
        get() = FileCacheManager.LANGUAGES_FILE
}