package com.example.podcastapp.data.data_source.local.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PodcastDetailsWithEpisodes(
    @Embedded
    val podcast: PodcastDetailsEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "podcastId"
    )
    val episodes: List<EpisodeEntity>
)
