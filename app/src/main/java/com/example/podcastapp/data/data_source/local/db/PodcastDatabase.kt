package com.example.podcastapp.data.data_source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.podcastapp.data.data_source.local.db.dao.GenresDao
import com.example.podcastapp.data.data_source.local.db.dao.LanguagesDao
import com.example.podcastapp.data.data_source.local.db.dao.PodcastDao
import com.example.podcastapp.data.data_source.local.db.dao.PodcastDetailsDao
import com.example.podcastapp.data.data_source.local.db.dao.PodcastRemoteKeysDao
import com.example.podcastapp.data.data_source.local.db.dao.RegionsDao
import com.example.podcastapp.data.data_source.local.db.entity.EpisodeEntity
import com.example.podcastapp.data.data_source.local.db.entity.GenreEntity
import com.example.podcastapp.data.data_source.local.db.entity.LanguageEntity
import com.example.podcastapp.data.data_source.local.db.entity.PodcastDetailsEntity
import com.example.podcastapp.data.data_source.local.db.entity.PodcastEntity
import com.example.podcastapp.data.data_source.local.db.entity.PodcastRemoteKeys
import com.example.podcastapp.data.data_source.local.db.entity.RegionEntity

@Database(
    [
        PodcastEntity::class,
        PodcastRemoteKeys::class,
        PodcastDetailsEntity::class,
        EpisodeEntity::class,
        GenreEntity::class,
        RegionEntity::class,
        LanguageEntity::class
    ],
    version = 1
)
@TypeConverters(DataConverter::class)
abstract class PodcastDatabase : RoomDatabase() {

    abstract fun podcastDao(): PodcastDao
    abstract fun podcastRemoteKeysDao(): PodcastRemoteKeysDao
    abstract fun podcastDetailsDao(): PodcastDetailsDao
    abstract fun genresDao(): GenresDao
    abstract fun languagesDao(): LanguagesDao
    abstract fun regionsDao(): RegionsDao
}