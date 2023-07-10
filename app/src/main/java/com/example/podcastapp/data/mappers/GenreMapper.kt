package com.example.podcastapp.data.mappers

import com.example.podcastapp.data.data_source.local.db.entity.GenreEntity
import com.example.podcastapp.data.data_source.remote.dto.GenreDto
import com.example.podcastapp.domain.model.Genre

fun GenreDto.asGenre(): Genre {
    return Genre(
        id = id,
        name = name,
        parentId = parentId
    )
}

fun GenreDto.asGenreEntity(): GenreEntity {
    return GenreEntity(
        id = id,
        name = name,
        parentId = parentId
    )
}

fun GenreEntity.asGenre(): Genre {
    return Genre(
        id = id,
        name = name,
        parentId = parentId
    )
}

fun Genre.asGenreEntity(): GenreEntity {
    return GenreEntity(
        id = id,
        name = name,
        parentId = parentId
    )
}