package com.example.podcastapp.di

import android.content.Context
import androidx.room.Room
import com.example.podcastapp.data.data_source.local.db.DataConverter
import com.example.podcastapp.data.data_source.local.db.PodcastDatabase
import com.example.podcastapp.data.data_source.local.file_store.FileCacheManager
import com.example.podcastapp.data.data_source.local.file_store.FileCacheManagerImpl
import com.example.podcastapp.data.data_source.local.file_store.FileCacheManagers
import com.example.podcastapp.data.data_source.local.file_store.GenresFileCacheManager
import com.example.podcastapp.data.data_source.local.file_store.JsonConverter
import com.example.podcastapp.data.data_source.local.file_store.JsonConverterImpl
import com.example.podcastapp.data.data_source.local.file_store.LanguagesFileCacheManager
import com.example.podcastapp.data.data_source.local.file_store.RegionsFileCacheManager
import com.example.podcastapp.data.data_source.remote.ApiRoutes
import com.example.podcastapp.data.data_source.remote.PodcastApi
import com.example.podcastapp.data.repository.ConfigCacheRepositoryImpl
import com.example.podcastapp.data.repository.PodcastDetailsRepositoryImpl
import com.example.podcastapp.data.repository.PodcastRepositoryImpl
import com.example.podcastapp.domain.repository.ConfigCacheRepository
import com.example.podcastapp.domain.repository.PodcastDetailsRepository
import com.example.podcastapp.domain.repository.PodcastRepository
import com.example.podcastapp.other.connection.InternetConnectionObserver
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.serialization.gson.gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpClient() =
        HttpClient(Android) {
            defaultRequest {
                url(ApiRoutes.BASE_URL)
                headers {
                    append("X-ListenAPI-Key", "2e9a0504bed64edfb0f2c71400bcebcb")
                }
            }
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                gson()
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 10000
                requestTimeoutMillis = 10000
                socketTimeoutMillis = 10000
            }
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain
                    .request()
                    .newBuilder()
                    .addHeader("X-ListenAPI-Key", "2e9a0504bed64edfb0f2c71400bcebcb")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Singleton
    @Provides
    fun providePodcastApi(httpClient: OkHttpClient): PodcastApi {
//        return PodcastApiImpl(
//            client = httpClient
//        )
        return Retrofit.Builder()
            .baseUrl(ApiRoutes.TEST_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(PodcastApi::class.java)
    }

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
    fun providePodcastRepository(
        @ApplicationContext context: Context,
        api: PodcastApi,
        db: PodcastDatabase,
        fileCacheManagers: FileCacheManagers
    ): PodcastRepository {
        return PodcastRepositoryImpl(
            api,
            db,
            fileCacheManagers,
            InternetConnectionObserver(context)
        )
    }

    @Singleton
    @Provides
    fun provideConfigCacheRepository(
        @ApplicationContext context: Context,
        api: PodcastApi,
        fileCacheManagers: FileCacheManagers
    ): ConfigCacheRepository {
        return ConfigCacheRepositoryImpl(
            api,
            fileCacheManagers,
            InternetConnectionObserver(context)
        )
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