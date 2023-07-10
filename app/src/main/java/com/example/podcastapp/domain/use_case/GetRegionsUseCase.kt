package com.example.podcastapp.domain.use_case

import com.example.podcastapp.domain.model.Regions
import com.example.podcastapp.domain.repository.PodcastRepository
import com.example.podcastapp.domain.repository.RegionsRepository
import com.example.podcastapp.other.Resource
import javax.inject.Inject

class GetRegionsUseCase @Inject constructor(
    private val regionsRepository: RegionsRepository
) {

    suspend operator fun invoke(): Resource<Regions> {
        return regionsRepository.getRegions()
    }
}