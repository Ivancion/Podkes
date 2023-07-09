package com.example.podcastapp.domain.model

data class PodcastDetails(
    val id: String,
    val title: String,
    val publisher: String,
    val image: String,
    val totalEpisodes: Int,
    val description: String,
    val language: String,
    val country: String,
    val episodes: List<Episode>,
    val nextEpisodePubDate: Long
)
