package com.example.podcastapp.domain.repository

import com.example.podcastapp.domain.model.Languages
import com.example.podcastapp.other.Resource

interface LanguagesRepository {

    suspend fun cacheLanguages(): Resource<Unit>

    suspend fun getLanguages(): Resource<Languages>
}