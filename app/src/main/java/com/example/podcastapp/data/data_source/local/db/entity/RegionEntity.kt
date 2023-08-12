package com.example.podcastapp.data.data_source.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "regions_table")
data class RegionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val shortName: String,
    val fullName: String
)
