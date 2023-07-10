package com.example.podcastapp.domain.repository

import androidx.paging.PagingData
import com.example.podcastapp.domain.model.Genre
import com.example.podcastapp.domain.model.Languages
import com.example.podcastapp.domain.model.Podcast
import com.example.podcastapp.domain.model.Regions
import com.example.podcastapp.other.Resource
import kotlinx.coroutines.flow.Flow

interface PodcastRepository {

    fun getBestPodcasts(
        genreId: Int?,
        region: String?,
        language: String?
    ): Flow<PagingData<Podcast>>
}