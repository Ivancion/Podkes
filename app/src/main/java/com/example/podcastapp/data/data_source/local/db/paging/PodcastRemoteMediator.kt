package com.example.podcastapp.data.data_source.local.db.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.podcastapp.data.data_source.local.db.PodcastDatabase
import com.example.podcastapp.data.data_source.local.db.entity.PodcastEntity
import com.example.podcastapp.data.data_source.remote.PodcastApi
import com.example.podcastapp.data.data_source.remote.dto.BestPodcastDto
import com.example.podcastapp.data.data_source.local.db.entity.PodcastRemoteKeys
import com.example.podcastapp.data.mappers.asPodcastEntity

@OptIn(ExperimentalPagingApi::class)
class PodcastRemoteMediator(
    private val podcastApi: PodcastApi,
    private val podcastDatabase: PodcastDatabase,
    private val genreId: Int? = null,
    private val region: String? = null,
    private val language: String? = null
) : RemoteMediator<Int, PodcastEntity>() {

    private val podcastDao = podcastDatabase.podcastDao()
    private val podcastRemoteKeysDao = podcastDatabase.podcastRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PodcastEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeysForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    nextPage
                }
            }

            Log.d("RemoteMediator", currentPage.toString())

            val response = podcastApi.getBestPodcasts(
                page = currentPage,
                genreId = genreId,
                region = region,
                language = language
            )
            val endOfPaginationReached = !response.hasNext

            val prevPage = if(currentPage == 1) null else currentPage - 1
            val nextPage = if(endOfPaginationReached) null else currentPage + 1

            podcastDatabase.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    podcastDao.deleteAllPodcasts()
                    podcastRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.podcasts.map { podcast ->
                    PodcastRemoteKeys(
                        id = podcast.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                podcastRemoteKeysDao.addRemoteKeys(keys)
                podcastDao.addPodcasts(response.podcasts.map { it.asPodcastEntity() })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeysForLastItem(
        state: PagingState<Int, PodcastEntity>
    ): PodcastRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { podcast ->
                podcastRemoteKeysDao.getRemoteKeys(podcast.id)
            }
    }
}