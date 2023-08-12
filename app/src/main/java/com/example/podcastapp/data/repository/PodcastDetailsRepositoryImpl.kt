package com.example.podcastapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.podcastapp.domain.mappers.asEpisode
import com.example.podcastapp.domain.mappers.asPodcastDetails
import com.example.podcastapp.data.data_source.remote.PodcastApi
import com.example.podcastapp.data.data_source.remote.paging.EpisodesPagingSource
import com.example.podcastapp.domain.model.Episode
import com.example.podcastapp.domain.model.PodcastDetails
import com.example.podcastapp.domain.repository.PodcastDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class PodcastDetailsRepositoryImpl(
    private val api: PodcastApi
): PodcastDetailsRepository {

    private val _podcastDetails = MutableStateFlow<PodcastDetails?>(null)
    override val podcastDetails: StateFlow<PodcastDetails?>
        get() = _podcastDetails.asStateFlow()

    override fun getPodcastEpisodes(id: String): Flow<PagingData<Episode>> {
        return Pager(
            PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                EpisodesPagingSource(
                    api = api,
                    podcastId = id,
                    podcastDetailsData = {
                        _podcastDetails.value = it.asPodcastDetails()
                    }
                )
            }
        )
            .flow
            .map { pagingData ->
                pagingData.map { it.asEpisode() }
            }
    }
}