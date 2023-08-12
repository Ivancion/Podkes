package com.example.podcastapp.domain.mappers

import com.example.podcastapp.data.data_source.local.db.entity.PodcastEntity
import com.example.podcastapp.data.data_source.remote.dto.BestPodcastDto
import com.example.podcastapp.data.data_source.remote.dto.SearchPodcastDto
import com.example.podcastapp.domain.model.Podcast

fun BestPodcastDto.asPodcastEntity(): PodcastEntity {
    return PodcastEntity(
        id = id,
        title = title,
        publisher = publisher,
        totalEpisodes = totalEpisodes,
        image = image,
        language = language,
        country = country,
        genreIds = genreIds
    )
}

fun PodcastEntity.asPodcast(): Podcast {
    return Podcast(
        id = id,
        title = title,
        publisher = publisher,
        image = image
    )
}

fun SearchPodcastDto.asPodcast(): Podcast {
    return Podcast(
        id = id,
        title = titleOriginal,
        publisher = publisherOriginal,
        image = image
    )
}

