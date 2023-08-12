package com.example.podcastapp.domain.repository

import androidx.paging.PagingData
import com.example.podcastapp.domain.model.Podcast
import kotlinx.coroutines.flow.Flow

interface PodcastRepository {

    fun getBestPodcasts(
        genreId: Int?,
        region: String?,
        language: String?
    ): Flow<PagingData<Podcast>>

    fun searchPodcasts(
        query: String,
        genreId: Int?,
        region: String?,
        language: String?
    ): Flow<PagingData<Podcast>>
}