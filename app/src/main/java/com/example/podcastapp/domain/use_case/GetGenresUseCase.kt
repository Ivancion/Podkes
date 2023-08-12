package com.example.podcastapp.domain.use_case

import com.example.podcastapp.domain.model.Genre
import com.example.podcastapp.domain.repository.GenresRepository
import com.example.podcastapp.other.Resource
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val genresRepository: GenresRepository
) {

    suspend operator fun invoke(): List<Genre> {
        return genresRepository.getAllGenres()
    }
}