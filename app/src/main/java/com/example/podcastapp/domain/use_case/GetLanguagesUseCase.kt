package com.example.podcastapp.domain.use_case

import com.example.podcastapp.domain.model.Languages
import com.example.podcastapp.domain.repository.PodcastRepository
import com.example.podcastapp.other.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLanguagesUseCase @Inject constructor(
    private val podcastRepository: PodcastRepository
) {

    suspend operator fun invoke(): Resource<Languages> {
        return podcastRepository.getLanguages()
    }
}