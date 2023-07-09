package com.example.podcastapp.data.repository

import com.example.podcastapp.data.data_source.local.file_store.FileCacheManager
import com.example.podcastapp.data.data_source.local.file_store.FileCacheManagers
import com.example.podcastapp.data.data_source.remote.PodcastApi
import com.example.podcastapp.domain.repository.ConfigCacheRepository
import com.example.podcastapp.other.Resource
import com.example.podcastapp.other.connection.InternetConnectionObserver

class ConfigCacheRepositoryImpl(
    private val api: PodcastApi,
    private val fileCacheManagers: FileCacheManagers,
    private val internetConnectionObserver: InternetConnectionObserver
): ConfigCacheRepository {

    private suspend fun <T> loadData(
        fileCacheManager: FileCacheManager<T>,
        apiCall: suspend () -> T,
    ): Resource<Unit> {
        return when {
            !internetConnectionObserver.isInternetAvailable() && !fileCacheManager.isFileExists() -> {
                Resource.Error("No internet connection and no cached data yet.")
            }
            internetConnectionObserver.isInternetAvailable() && !fileCacheManager.isFileExists() -> {
                try {
                    val data = apiCall()
                    try {
                        fileCacheManager.writeData(data)
                        Resource.Success(Unit)
                    } catch (e: Exception) {
                        Resource.Error("Can't cache data.")
                    }
                } catch (e: Exception) {
                    Resource.Error("Server exception and no cached data yet.")
                }
            }
            internetConnectionObserver.isInternetAvailable() && fileCacheManager.isFileExists() -> {
                try {
                    val data = apiCall()
                    try {
                        fileCacheManager.writeData(data)
                        Resource.Success(Unit)
                    } catch (e: Exception) {
                        Resource.Error("Can't cache data.")
                    }
                } catch (e: Exception) {
                    Resource.Success(Unit)
                }
            }
            !internetConnectionObserver.isInternetAvailable() && fileCacheManager.isFileExists() -> {
                Resource.Success(Unit)
            }
            else -> Resource.Error("Unknown error.")
        }
    }

    override suspend fun loadGenres(): Resource<Unit> {
        return loadData(fileCacheManagers.genresFileCacheManager) {
            api.getGenres()
        }
    }

    override suspend fun loadRegions(): Resource<Unit> {
        return loadData(fileCacheManagers.regionsFileCacheManager) {
            api.getRegions()
        }
    }

    override suspend fun loadLanguages(): Resource<Unit> {
        return loadData(fileCacheManagers.languagesFileCacheManager) {
            api.getLanguages()
        }
    }
}