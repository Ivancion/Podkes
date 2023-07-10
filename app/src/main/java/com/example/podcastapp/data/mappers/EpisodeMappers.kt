package com.example.podcastapp.data.mappers

import com.example.podcastapp.data.data_source.local.db.entity.EpisodeEntity
import com.example.podcastapp.data.data_source.remote.dto.EpisodeDto
import com.example.podcastapp.domain.model.Episode

fun EpisodeDto.asEpisodeEntity(podcastId: String): EpisodeEntity {
    return EpisodeEntity(
        podcastId = podcastId,
        id = id,
        title = title,
        description = description,
        audio = audio,
        pubDateMs = pubDateMs,
        audioLengthSec = audioLengthSec,
        image = image,
        maybeAudioInvalid = maybeAudioInvalid
    )
}

fun EpisodeDto.asEpisode(): Episode {
    return Episode(
        id = id,
        title = title,
        description = description,
        audio = audio,
        pubDateMs = pubDateMs,
        audioLengthSec = audioLengthSec,
        image = image,
        maybeAudioInvalid = maybeAudioInvalid
    )
}

fun EpisodeEntity.asEpisode(): Episode {
    return Episode(
        id = id,
        title = title,
        description = description,
        audio = audio,
        pubDateMs = pubDateMs,
        audioLengthSec = audioLengthSec,
        image = image,
        maybeAudioInvalid = maybeAudioInvalid
    )
}