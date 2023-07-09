package com.example.podcastapp.data.data_source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.podcastapp.data.data_source.local.db.dao.PodcastDao
import com.example.podcastapp.data.data_source.local.db.dao.PodcastRemoteKeysDao
import com.example.podcastapp.data.data_source.local.db.entity.EpisodeEntity
import com.example.podcastapp.data.data_source.local.db.entity.PodcastDetailsEntity
import com.example.podcastapp.data.data_source.local.db.entity.PodcastEntity
import com.example.podcastapp.data.data_source.remote.dto.BestPodcastDto
import com.example.podcastapp.data.data_source.local.db.entity.PodcastRemoteKeys

@Database([PodcastEntity::class, PodcastRemoteKeys::class, PodcastDetailsEntity::class, EpisodeEntity::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class PodcastDatabase: RoomDatabase() {

    abstract fun podcastDao(): PodcastDao
    abstract fun podcastRemoteKeysDao(): PodcastRemoteKeysDao
}