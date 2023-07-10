package com.example.podcastapp.di

import android.content.Context
import com.example.podcastapp.data.data_source.local.db.PodcastDatabase
import com.example.podcastapp.data.data_source.remote.PodcastApi
import com.example.podcastapp.data.repository.GenresRepositoryImpl
import com.example.podcastapp.data.repository.LanguagesRepositoryImpl
import com.example.podcastapp.data.repository.PodcastDetailsRepositoryImpl
import com.example.podcastapp.data.repository.PodcastRepositoryImpl
import com.example.podcastapp.data.repository.RegionsRepositoryImpl
import com.example.podcastapp.domain.repository.GenresRepository
import com.example.podcastapp.domain.repository.LanguagesRepository
import com.example.podcastapp.domain.repository.PodcastDetailsRepository
import com.example.podcastapp.domain.repository.PodcastRepository
import com.example.podcastapp.domain.repository.RegionsRepository
import com.example.podcastapp.other.connection.InternetConnectionObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePodcastRepository(
        @ApplicationContext context: Context,
        api: PodcastApi,
        db: PodcastDatabase
    ): PodcastRepository {
        return PodcastRepositoryImpl(api, db)
    }

    @Singleton
    @Provides
    fun providePodcastDetailsRepository(
        api: PodcastApi
    ): PodcastDetailsRepository {
        return PodcastDetailsRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideGenresRepository(
        api: PodcastApi,
        db: PodcastDatabase,
        connectionObserver: InternetConnectionObserver
    ): GenresRepository {
        return GenresRepositoryImpl(api, db, connectionObserver)
    }

    @Singleton
    @Provides
    fun provideLanguagesRepository(
        api: PodcastApi,
        db: PodcastDatabase,
        connectionObserver: InternetConnectionObserver
    ): LanguagesRepository {
        return LanguagesRepositoryImpl(api, db, connectionObserver)
    }

    @Singleton
    @Provides
    fun provideRegionsRepository(
        api: PodcastApi,
        db: PodcastDatabase,
        connectionObserver: InternetConnectionObserver
    ): RegionsRepository {
        return RegionsRepositoryImpl(api, db, connectionObserver)
    }
}