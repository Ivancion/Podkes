package com.example.podcastapp.domain.use_case

import com.example.podcastapp.domain.repository.ConfigCacheRepository
import com.example.podcastapp.domain.repository.RegionsRepository
import com.example.podcastapp.other.Resource
import javax.inject.Inject

class CacheRegionsUseCase @Inject constructor(
    private val regionsRepository: RegionsRepository
) {

    suspend operator fun invoke(): Resource<Unit> {
        return regionsRepository.cacheRegions()
    }
}