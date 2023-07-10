package com.example.podcastapp.data.repository

import com.example.podcastapp.data.data_source.local.db.PodcastDatabase
import com.example.podcastapp.data.data_source.remote.PodcastApi
import com.example.podcastapp.data.mappers.asLanguageEntities
import com.example.podcastapp.data.mappers.asLanguages
import com.example.podcastapp.domain.model.Languages
import com.example.podcastapp.domain.repository.LanguagesRepository
import com.example.podcastapp.other.Resource
import com.example.podcastapp.other.connection.InternetConnectionObserver

class LanguagesRepositoryImpl(
    private val api: PodcastApi,
    private val db: PodcastDatabase,
    private val connectionObserver: InternetConnectionObserver
): LanguagesRepository, UtilDataRepositoryHelper<Languages> by UtilDataRepositoryHelperImpl() {

    override suspend fun cacheLanguages(): Resource<Unit> {
        return cacheData(
            getDataFromNetwork = ::getDataFromNetwork,
            saveDataIntoDb = ::saveDataIntoDb,
            haveInternetConnection = connectionObserver.isInternetAvailable(),
            haveCachedData = haveCachedData()
        )
    }

    override suspend fun getLanguages(): Resource<Languages> {
        return getData(
            getDataFromNetwork = ::getDataFromNetwork,
            getDataFromDb = {
                db.languagesDao().getAllLanguages().asLanguages()
            },
            saveDataIntoDb = ::saveDataIntoDb,
            haveInternetConnection = connectionObserver.isInternetAvailable(),
            haveCachedData = haveCachedData()
        )
    }

    private suspend fun saveDataIntoDb(languages: Languages) {
        db.languagesDao().upsertLanguages(languages.asLanguageEntities())
    }

    private suspend fun getDataFromNetwork(): Languages {
        return api.getLanguages().asLanguages()
    }

    private suspend fun haveCachedData(): Boolean {
        return db.languagesDao().getAllLanguages().isNotEmpty()
    }
}