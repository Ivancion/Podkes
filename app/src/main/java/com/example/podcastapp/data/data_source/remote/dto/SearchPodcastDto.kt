package com.example.podcastapp.data.data_source.remote.dto

import com.google.gson.annotations.SerializedName

data class SearchPodcastDto(
    @SerializedName("audio_length_sec")
    val audioLengthSec: Int,
    @SerializedName("description_highlighted")
    val descriptionHighlighted: String,
    @SerializedName("description_original")
    val descriptionOriginal: String,
    @SerializedName("earliest_pub_date_ms")
    val earliestPubDateMs: Long,
    @SerializedName("explicit_content")
    val explicitContent: Boolean,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val id: String,
    val image: String,
    @SerializedName("itunes_id")
    val itunesId: Int,
    @SerializedName("latest_pub_date_ms")
    val latestPubDateMs: Long,
    @SerializedName("listennotes_url")
    val listenNotesUrl: String,
    @SerializedName("publisher_highlighted")
    val publisherHighlighted: String,
    @SerializedName("publisher_original")
    val publisherOriginal: String,
    val thumbnail: String,
    @SerializedName("title_highlighted")
    val titleHighlighted: String,
    @SerializedName("title_original")
    val titleOriginal: String,
    @SerializedName("total_episodes")
    val totalEpisodes: Int,
    @SerializedName("update_frequency_hours")
    val updateFrequencyHours: Int,
    val website: String
)