package com.example.podcastapp.domain.use_case

import com.example.podcastapp.domain.model.Languages
import com.example.podcastapp.domain.repository.LanguagesRepository
import com.example.podcastapp.domain.repository.PodcastRepository
import com.example.podcastapp.other.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLanguagesUseCase @Inject constructor(
    private val languagesRepository: LanguagesRepository
) {

    suspend operator fun invoke(): Resource<Languages> {
        return languagesRepository.getLanguages()
    }
}