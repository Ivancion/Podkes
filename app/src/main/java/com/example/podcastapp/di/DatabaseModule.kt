package com.example.podcastapp.di

import android.content.Context
import androidx.room.Room
import com.example.podcastapp.data.data_source.local.db.DataConverter
import com.example.podcastapp.data.data_source.local.db.PodcastDatabase
import com.example.podcastapp.data.data_source.local.file_store.FileCacheManagers
import com.example.podcastapp.data.data_source.local.file_store.GenresFileCacheManager
import com.example.podcastapp.data.data_source.local.file_store.JsonConverter
import com.example.podcastapp.data.data_source.local.file_store.JsonConverterImpl
import com.example.podcastapp.data.data_source.local.file_store.LanguagesFileCacheManager
import com.example.podcastapp.data.data_source.local.file_store.RegionsFileCacheManager
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideFileCacheManagers(
        @ApplicationContext context: Context,
        jsonConverter: JsonConverter
    ): FileCacheManagers {
        return FileCacheManagers(
            genresFileCacheManager = GenresFileCacheManager(context, jsonConverter),
            regionsFileCacheManager = RegionsFileCacheManager(context, jsonConverter),
            languagesFileCacheManager = LanguagesFileCacheManager(context, jsonConverter)
        )
    }

    @Singleton
    @Provides
    fun provideJsonConverter(): JsonConverter {
        return JsonConverterImpl(GsonBuilder().create())
    }

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
        jsonConverter: JsonConverter
    ): PodcastDatabase {
        return Room.databaseBuilder(
            context,
            PodcastDatabase::class.java,
            "podcast_database.db"
        )
            .addTypeConverter(DataConverter(jsonConverter))
            .build()
    }
}