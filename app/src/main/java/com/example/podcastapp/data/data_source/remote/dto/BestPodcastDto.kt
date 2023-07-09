package com.example.podcastapp.data.data_source.remote.dto

import com.google.gson.annotations.SerializedName

data class BestPodcastDto(
    @SerializedName("audio_length_sec")
    val audioLengthSec: Int,
    val country: String,
    val description: String,
    @SerializedName("earliest_pub_date_ms")
    val earliestPubDateMs: Long,
    @SerializedName("explicit_content")
    val explicitContent: Boolean,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val id: String,
    val image: String,
    @SerializedName("is_claimed")
    val isClaimed: Boolean,
    @SerializedName("itunes_id")
    val itunesId: Int,
    val language: String,
    @SerializedName("latest_pub_date_ms")
    val latestPubDateMs: Long,
    @SerializedName("listennotes_url")
    val listenNotesUrl: String,
    val publisher: String,
    val thumbnail: String,
    val title: String,
    @SerializedName("total_episodes")
    val totalEpisodes: Int,
    val type: String,
    @SerializedName("update_frequency_hours")
    val updateFrequencyHours: Int,
    val website: String? = null
)