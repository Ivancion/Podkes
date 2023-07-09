package com.example.podcastapp.data.data_source.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.podcastapp.domain.model.Episode
import com.google.gson.annotations.SerializedName

@Entity(tableName = "podcast_details_table")
data class PodcastDetailsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val publisher: String,
    val image: String,
    val totalEpisodes: Int,
    val description: String,
    val language: String,
    val country: String,
    val nextEpisodePubDate: Long
)