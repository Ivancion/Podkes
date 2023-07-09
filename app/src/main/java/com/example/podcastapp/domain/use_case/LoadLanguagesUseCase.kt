package com.example.podcastapp.domain.use_case

import com.example.podcastapp.domain.repository.ConfigCacheRepository
import com.example.podcastapp.domain.repository.PodcastRepository
import com.example.podcastapp.other.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadLanguagesUseCase @Inject constructor(
    private val configCacheRepository: ConfigCacheRepository
) {

    suspend operator fun invoke(): Resource<Unit> {
        return configCacheRepository.loadLanguages()
    }
}