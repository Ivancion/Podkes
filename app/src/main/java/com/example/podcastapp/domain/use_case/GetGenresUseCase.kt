package com.example.podcastapp.domain.use_case

import com.example.podcastapp.domain.model.Genre
import com.example.podcastapp.domain.repository.PodcastRepository
import com.example.podcastapp.other.Resource
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val podcastRepository: PodcastRepository
) {

    suspend operator fun invoke(): Resource<List<Genre>> {
        return podcastRepository.getGenres()
    }
}