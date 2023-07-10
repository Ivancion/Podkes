package com.example.podcastapp.data.data_source.local.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "episodes_table",
    foreignKeys = [
        ForeignKey(
            entity = PodcastDetailsEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("podcastId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
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
