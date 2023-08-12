package com.example.podcastapp.domain.use_case

import com.example.podcastapp.domain.model.Region
import com.example.podcastapp.domain.repository.RegionsRepository
import javax.inject.Inject

class GetRegionsUseCase @Inject constructor(
    private val regionsRepository: RegionsRepository
) {

    suspend operator fun invoke(): List<Region> {
        return regionsRepository.getRegions()
    }
}