package com.example.podcastapp.di

import android.content.Context
import com.example.podcastapp.presentation.services.podcast_service.PodcastServiceConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePodcastServiceConnection(
        @ApplicationContext context: Context
    ) = PodcastServiceConnection(context)

}