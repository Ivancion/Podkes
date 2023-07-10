package com.example.podcastapp.data.repository

import com.example.podcastapp.data.data_source.local.db.PodcastDatabase
import com.example.podcastapp.data.data_source.remote.PodcastApi
import com.example.podcastapp.data.mappers.asRegions
import com.example.podcastapp.data.mappers.asRegionsEntity
import com.example.podcastapp.domain.model.Regions
import com.example.podcastapp.domain.repository.RegionsRepository
import com.example.podcastapp.other.Resource
import com.example.podcastapp.other.connection.InternetConnectionObserver

class RegionsRepositoryImpl(
    private val api: PodcastApi,
    private val db: PodcastDatabase,
    private val connectionObserver: InternetConnectionObserver
): RegionsRepository, UtilDataRepositoryHelper<Regions> by UtilDataRepositoryHelperImpl() {

    override suspend fun cacheRegions(): Resource<Unit> {
        return cacheData(
            getDataFromNetwork = ::getDataFromNetwork,
            saveDataIntoDb = ::saveDataIntoDb,
            haveInternetConnection = connectionObserver.isInternetAvailable(),
            haveCachedData = haveCachedData()
        )
    }

    override suspend fun getRegions(): Resource<Regions> {
        return getData(
            getDataFromNetwork = ::getDataFromNetwork,
            getDataFromDb = {
                db.regionsDao().getRegions()!!.asRegions()
            },
            saveDataIntoDb = ::saveDataIntoDb,
            haveInternetConnection = connectionObserver.isInternetAvailable(),
            haveCachedData = haveCachedData()
        )
    }

    private suspend fun saveDataIntoDb(regions: Regions) {
        db.regionsDao().upsertRegions(regions.asRegionsEntity())
    }

    private suspend fun getDataFromNetwork(): Regions {
        return api.getRegions().regions.asRegions()
    }

    private suspend fun haveCachedData(): Boolean {
        return db.regionsDao().getRegions() != null
    }
}