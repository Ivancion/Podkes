package com.example.podcastapp.domain.repository

import androidx.paging.PagingData
import com.example.podcastapp.domain.model.Episode
import com.example.podcastapp.domain.model.PodcastDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface PodcastDetailsRepository {

    val podcastDetails: StateFlow<PodcastDetails?>

    fun getPodcastEpisodes(
        id: String
    ): Flow<PagingData<Episode>>
}