package com.example.podcastapp.data.data_source.mappers

import com.example.podcastapp.data.data_source.remote.dto.GenreDto
import com.example.podcastapp.domain.model.Genre

fun GenreDto.asGenre(): Genre {
    return Genre(
        id = id,
        name = name
    )
}