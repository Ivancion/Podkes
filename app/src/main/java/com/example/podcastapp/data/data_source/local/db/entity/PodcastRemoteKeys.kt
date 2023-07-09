package com.example.podcastapp.data.data_source.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "podcasts_remote_keys_table")
data class PodcastRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)
