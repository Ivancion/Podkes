package com.example.podcastapp.domain.model

data class Podcast(
    val id: String,
    val title: String,
    val publisher: String,
    val image: String,
    val totalEpisodes: Int,
    val language: String,
    val country: String,
    val genreIds: List<Int>
)
