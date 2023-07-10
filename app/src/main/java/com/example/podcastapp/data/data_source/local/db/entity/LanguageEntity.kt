package com.example.podcastapp.data.data_source.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languages_table")
data class LanguageEntity(
    @PrimaryKey(autoGenerate = false)
    val name: String
)