package com.example.podcastapp.data.data_source.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes_table")
data class EpisodeEntity(
    val podcastId: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val description: String,
    val audio: String,
    val pubDateMs: Long,
    val audioLengthSec: Int,
    val image: String,
    val maybeAudioInvalid: Boolean
)
