package com.example.podcastapp.domain.mappers

import android.net.Uri
import androidx.core.os.bundleOf
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.podcastapp.data.data_source.local.db.entity.EpisodeEntity
import com.example.podcastapp.data.data_source.remote.dto.EpisodeDto
import com.example.podcastapp.domain.model.Episode
import com.example.podcastapp.domain.model.PodcastDetails

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

fun Episode.asMediaItem(podcastDetails: PodcastDetails): MediaItem {
    val mediaMetadata = MediaMetadata.Builder()
        .setArtist(podcastDetails.publisher)
        .setTitle(title)
        .setDisplayTitle(title)
        .setArtworkUri(Uri.parse(image))
        .setDescription(description)
        .setIsPlayable(true)
        .setExtras(
            bundleOf(
                "episode_duration" to audioLengthSec
            )
        )
        .build()
    return MediaItem.Builder()
        .setMediaId(this.id)
        .setMediaMetadata(mediaMetadata)
        .setUri(audio)
        .build()
}