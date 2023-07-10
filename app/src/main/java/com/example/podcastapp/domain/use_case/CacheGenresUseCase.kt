package com.example.podcastapp.domain.use_case

import com.example.podcastapp.domain.repository.ConfigCacheRepository
import com.example.podcastapp.domain.repository.GenresRepository
import com.example.podcastapp.other.Resource
import javax.inject.Inject

class CacheGenresUseCase @Inject constructor(
    private val genresRepository: GenresRepository
) {

    suspend operator fun invoke(): Resource<Unit> {
        return genresRepository.cacheGenres()
    }
}