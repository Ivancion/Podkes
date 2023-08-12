package com.example.podcastapp.data.repository

import com.example.podcastapp.domain.mappers.asGenre
import com.example.podcastapp.domain.mappers.asGenreEntity
import com.example.podcastapp.other.Resource

interface UtilDataRepositoryHelper<T> {

    suspend fun cacheData(
        getDataFromNetwork: suspend () -> T,
        saveDataIntoDb: suspend (T) -> Unit,
        haveInternetConnection: Boolean,
        haveCachedData: Boolean
    ): Resource<Unit>

    suspend fun getData(
        getDataFromDb: suspend () -> T
    ): T
}

class UtilDataRepositoryHelperImpl<T>: UtilDataRepositoryHelper<T> {

    override suspend fun cacheData(
        getDataFromNetwork: suspend () -> T,
        saveDataIntoDb: suspend (T) -> Unit,
        haveInternetConnection: Boolean,
        haveCachedData: Boolean
    ): Resource<Unit> {
        if(!haveInternetConnection) {
            return if(haveCachedData) {
                Resource.Success(Unit)
            } else {
                Resource.Error("No internet connection.")
            }
        }
        return try {
            val data = getDataFromNetwork()
            saveDataIntoDb(data)
            Resource.Success(Unit)
        } catch (e: Exception) {
            if(haveCachedData) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Server exception.")
            }
        }
    }

    override suspend fun getData(getDataFromDb: suspend () -> T): T {
        return getDataFromDb()
    }

//    override suspend fun getData(
//        getDataFromNetwork: suspend () -> T,
//        getDataFromDb: suspend () -> T,
//        saveDataIntoDb: suspend (T) -> Unit,
//        haveInternetConnection: Boolean,
//        haveCachedData: Boolean
//    ): Resource<T> {
//        if(haveCachedData) {
//            val data = getDataFromDb()
//            return Resource.Success(data)
//        }
//        if(!haveInternetConnection) {
//            return Resource.Error("No internet connection.")
//        }
//        return try {
//            val data = getDataFromNetwork()
//            saveDataIntoDb(data)
//            Resource.Success(data)
//        } catch (e: Exception) {
//            Resource.Error("Server exception.")
//        }
//    }
}