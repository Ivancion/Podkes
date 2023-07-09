package com.example.podcastapp.domain.use_case

import androidx.paging.PagingData
import com.example.podcastapp.domain.model.Podcast
import com.example.podcastapp.domain.repository.PodcastRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBestPodcastsUseCase @Inject constructor(
    private val podcastRepository: PodcastRepository
) {

    operator fun invoke(
        genreId: Int? = null,
        region: String? = null,
        language: String? = null
    ): Flow<PagingData<Podcast>> {
        return podcastRepository.getBestPodcasts(genreId, region, language)
    }
}