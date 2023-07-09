package com.example.podcastapp.domain.model

data class Episode(
    val id: String,
    val title: String,
    val description: String,
    val audio: String,
    val pubDateMs: Long,
    val audioLengthSec: Int,
    val image: String,
    val maybeAudioInvalid: Boolean
)
