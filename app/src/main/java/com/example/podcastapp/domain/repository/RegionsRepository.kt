package com.example.podcastapp.domain.repository

import com.example.podcastapp.domain.model.Regions
import com.example.podcastapp.other.Resource

interface RegionsRepository {

    suspend fun cacheRegions(): Resource<Unit>

    suspend fun getRegions(): Resource<Regions>
}