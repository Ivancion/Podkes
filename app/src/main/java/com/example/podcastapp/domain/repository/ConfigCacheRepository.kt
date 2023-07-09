package com.example.podcastapp.domain.repository

import com.example.podcastapp.other.Resource

interface ConfigCacheRepository {
    suspend fun loadGenres(): Resource<Unit>

    suspend fun loadRegions(): Resource<Unit>

    suspend fun loadLanguages(): Resource<Unit>
}