package com.example.podcastapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.podcastapp.data.data_source.local.db.PodcastDatabase
import com.example.podcastapp.data.data_source.local.db.paging.PodcastRemoteMediator
import com.example.podcastapp.data.data_source.remote.PodcastApi
import com.example.podcastapp.data.mappers.asPodcast
import com.example.podcastapp.domain.model.Podcast
import com.example.podcastapp.domain.repository.PodcastRepository
import com.example.podcastapp.other.connection.InternetConnectionObserver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PodcastRepositoryImpl(
    private val api: PodcastApi,
    private val db: PodcastDatabase
) : PodcastRepository {

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