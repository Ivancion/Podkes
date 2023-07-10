package com.example.podcastapp.domain.use_case

import com.example.podcastapp.domain.repository.ConfigCacheRepository
import com.example.podcastapp.domain.repository.LanguagesRepository
import com.example.podcastapp.other.Resource
import javax.inject.Inject

class CacheLanguagesUseCase @Inject constructor(
    private val languagesRepository: LanguagesRepository
) {

    suspend operator fun invoke(): Resource<Unit> {
        return languagesRepository.cacheLanguages()
    }
}