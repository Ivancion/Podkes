package com.example.podcastapp.domain.use_case

import androidx.paging.PagingData
import com.example.podcastapp.domain.model.Podcast
import com.example.podcastapp.domain.repository.PodcastRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchPodcastsUseCase @Inject constructor(
    private val podcastRepository: PodcastRepository
) {

    operator fun invoke(query: String, genreId: Int?, language: String?, region: String?): Flow<PagingData<Podcast>> {
        return podcastRepository.searchPodcasts(query, genreId, region, language)
    }
}