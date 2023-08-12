package com.example.podcastapp.data.repository

import com.example.podcastapp.data.data_source.local.db.PodcastDatabase
import com.example.podcastapp.data.data_source.remote.PodcastApi
import com.example.podcastapp.domain.mappers.asRegion
import com.example.podcastapp.domain.mappers.asRegionEntity
import com.example.podcastapp.domain.mappers.asRegions
import com.example.podcastapp.domain.model.Region
import com.example.podcastapp.domain.repository.RegionsRepository
import com.example.podcastapp.other.Resource
import com.example.podcastapp.other.connection.InternetConnectionObserver

class RegionsRepositoryImpl(
    private val api: PodcastApi,
    private val db: PodcastDatabase,
    private val connectionObserver: InternetConnectionObserver
): RegionsRepository, UtilDataRepositoryHelper<List<Region>> by UtilDataRepositoryHelperImpl() {

    override suspend fun cacheRegions(): Resource<Unit> {
        return cacheData(
            getDataFromNetwork = ::getDataFromNetwork,
            saveDataIntoDb = ::saveDataIntoDb,
            haveInternetConnection = connectionObserver.isInternetAvailable(),
            haveCachedData = haveCachedData()
        )
    }

    override suspend fun getRegions(): List<Region> {
        return getData(
            getDataFromDb = {
                db.regionsDao().getRegions().map { it.asRegion() }
            }
        )
    }

    private suspend fun saveDataIntoDb(regions: List<Region>) {
        db.regionsDao().upsertRegions(regions.map { it.asRegionEntity() })
    }

    private suspend fun getDataFromNetwork(): List<Region> {
        return api.getRegions().regions.asRegions()
    }

    private suspend fun haveCachedData(): Boolean {
        return db.regionsDao().getRegions().isNotEmpty()
    }
}