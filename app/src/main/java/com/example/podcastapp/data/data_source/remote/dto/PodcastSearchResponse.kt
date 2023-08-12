package com.example.podcastapp.data.data_source.remote.dto

data class PodcastSearchResponse(
    val count: Int,
    val next_offset: Int,
    val results: List<SearchPodcastDto>,
    val took: Double,
    val total: Int
)