package com.example.podcastapp.data.data_source.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("podcast_table")
data class PodcastEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val publisher: String,
    val image: String,
    val totalEpisodes: Int,
    val language: String,
    val country: String,
    val genreIds: List<Int>
)