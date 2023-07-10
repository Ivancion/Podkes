package com.example.podcastapp.data.mappers

import com.example.podcastapp.data.data_source.local.db.entity.PodcastDetailsEntity
import com.example.podcastapp.data.data_source.local.db.entity.PodcastDetailsWithEpisodes
import com.example.podcastapp.data.data_source.remote.dto.PodcastDetailsDto
import com.example.podcastapp.domain.model.PodcastDetails

fun PodcastDetailsDto.asPodcastDetailsEntity(): PodcastDetailsEntity {
    return PodcastDetailsEntity(
        id = id,
        title = title,
        publisher = publisher,
        image = image,
        totalEpisodes = totalEpisodes,
        description = description,
        language = language,
        country = country,
        nextEpisodePubDate = nextEpisodePubDate
    )
}

fun PodcastDetailsDto.asPodcastDetails(): PodcastDetails {
    return PodcastDetails(
        id = id,
        title = title,
        publisher = publisher,
        image = image,
        totalEpisodes = totalEpisodes,
        description = description,
        language = language,
        country = country,
        episodes = episodes.map { it.asEpisode() },
        nextEpisodePubDate = nextEpisodePubDate
    )
}

fun PodcastDetailsWithEpisodes.asPodcastDetails(): PodcastDetails {
    return PodcastDetails(
        id = podcast.id,
        title = podcast.title,
        publisher = podcast.publisher,
        image = podcast.publisher,
        totalEpisodes = podcast.totalEpisodes,
        description = podcast.description,
        language = podcast.language,
        country = podcast.country,
        episodes = episodes.map { it.asEpisode() },
        nextEpisodePubDate = podcast.nextEpisodePubDate
    )
}