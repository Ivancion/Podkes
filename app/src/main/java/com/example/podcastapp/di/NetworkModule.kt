package com.example.podcastapp.di

import android.content.Context
import com.example.podcastapp.data.data_source.remote.ApiRoutes
import com.example.podcastapp.data.data_source.remote.PodcastApi
import com.example.podcastapp.other.connection.InternetConnectionObserver
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
object NetworkModule {

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
        return Retrofit.Builder()
            .baseUrl(ApiRoutes.TEST_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(PodcastApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkConnectionObserver(
        @ApplicationContext context: Context
    ) = InternetConnectionObserver(context)
}