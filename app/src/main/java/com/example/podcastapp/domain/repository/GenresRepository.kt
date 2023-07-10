package com.example.podcastapp.domain.repository

import com.example.podcastapp.domain.model.Genre
import com.example.podcastapp.other.Resource

interface GenresRepository {

    suspend fun cacheGenres(): Resource<Unit>

    suspend fun getAllGenres(): Resource<List<Genre>>

    suspend fun getGenresByIds(ids: List<Int>): List<Genre>
}