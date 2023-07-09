package com.example.podcastapp.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.podcastapp.data.data_source.local.db.PodcastDatabase
import com.example.podcastapp.data.data_source.local.db.paging.PodcastRemoteMediator
import com.example.podcastapp.data.data_source.local.file_store.FileCacheManager
import com.example.podcastapp.data.data_source.local.file_store.FileCacheManagers
import com.example.podcastapp.data.data_source.mappers.asGenre
import com.example.podcastapp.data.data_source.mappers.asLanguages
import com.example.podcastapp.data.data_source.mappers.asPodcast
import com.example.podcastapp.data.data_source.mappers.asRegions
import com.example.podcastapp.data.data_source.remote.PodcastApi
import com.example.podcastapp.data.data_source.remote.dto.GenresResponse
import com.example.podcastapp.data.data_source.remote.dto.LanguagesResponse
import com.example.podcastapp.data.data_source.remote.dto.RegionsResponse
import com.example.podcastapp.data.exceptions.CacheException
import com.example.podcastapp.domain.model.Genre
import com.example.podcastapp.domain.model.Languages
import com.example.podcastapp.domain.model.Podcast
import com.example.podcastapp.domain.model.Regions
import com.example.podcastapp.domain.repository.PodcastRepository
import com.example.podcastapp.other.Resource
import com.example.podcastapp.other.connection.InternetConnectionObserver
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.jvm.Throws

class PodcastRepositoryImpl(
    private val api: PodcastApi,
    private val db: PodcastDatabase,
    private val fileCacheManagers: FileCacheManagers,
    private val internetConnectionObserver: InternetConnectionObserver
) : PodcastRepository {

    @Throws(CacheException::class)
    private suspend fun <T> getNewOrCachedData(
        fileCacheManager: FileCacheManager<T>,
        cacheType: TypeToken<T>,
        apiCall: suspend () -> T
    ): T {
        return if (internetConnectionObserver.isInternetAvailable()) {
            try {
                apiCall()
            } catch (e: Exception) {
                try {
                    fileCacheManager.readData(cacheType)
                } catch (e: Exception) {
                    throw CacheException("Can't get cached data.")
                }
            }
        } else {
            try {
                fileCacheManager.readData(cacheType)
            } catch (e: Exception) {
                Log.d("PodcastRepository", e.stackTraceToString())
                throw CacheException("Can't get cached data.")
            }
        }
    }

    override suspend fun getGenres(): Resource<List<Genre>> {
        return try {
            val genres = getNewOrCachedData(fileCacheManagers.genresFileCacheManager, object: TypeToken<GenresResponse>() {}) {
                api.getGenres()
            }.genres.map { it.asGenre() }
            Resource.Success(genres)
        } catch(e: CacheException) {
            Resource.Error(e.msg)
        }
    }

    override suspend fun getRegions(): Resource<Regions> {
        return try {
            val regions = getNewOrCachedData(fileCacheManagers.regionsFileCacheManager, object: TypeToken<RegionsResponse>() {}) {
                api.getRegions()
            }.regions.asRegions()
            Resource.Success(regions)
        } catch (e: CacheException) {
            Resource.Error(e.msg)
        }
    }

    override suspend fun getLanguages(): Resource<Languages> {
        return try {
            val languages = getNewOrCachedData(fileCacheManagers.languagesFileCacheManager, object: TypeToken<LanguagesResponse>() {}) {
                api.getLanguages()
            }.asLanguages()
            Resource.Success(languages)
        } catch (e: CacheException) {
            Resource.Error(e.msg)
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getBestPodcasts(
        genreId: Int?,
        region: String?,
        language: String?
    ): Flow<PagingData<Podcast>> {
        val pagingSourceFactory = {
            db.podcastDao().getAllPodcasts()
//            PodcastPagingSource(
//                api
//            )
        }
        return Pager(
            config = PagingConfig(20),
            remoteMediator = PodcastRemoteMediator(
                podcastApi = api,
                podcastDatabase = db,
                genreId = genreId,
                region = region,
                language = language
            ),
            pagingSourceFactory = pagingSourceFactory
        )
            .flow
            .map { pagingData ->
                pagingData.map { it.asPodcast() }
            }
    }


}