package com.example.podcastapp.data.data_source.remote.dto

import com.google.gson.annotations.SerializedName

data class EpisodeDto(
    val audio: String,
    @SerializedName("audio_length_sec")
    val audioLengthSec: Int,
    val description: String,
    @SerializedName("explicit_content")
    val explicitContent: Boolean,
    @SerializedName("guid_from_rss")
    val guidFromRss: String,
    val id: String,
    val image: String,
    val link: String,
    @SerializedName("listennotes_edit_url")
    val listenNotesEditUrl: String,
    @SerializedName("listennotes_url")
    val listenNotesUrl: String,
    @SerializedName("maybe_audio_invalid")
    val maybeAudioInvalid: Boolean,
    @SerializedName("pub_date_ms")
    val pubDateMs: Long,
    val thumbnail: String,
    val title: String
)