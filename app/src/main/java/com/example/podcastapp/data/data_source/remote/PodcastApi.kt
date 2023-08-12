package com.example.podcastapp.data.data_source.remote

import com.example.podcastapp.data.data_source.remote.dto.BestPodcastsResponse
import com.example.podcastapp.data.data_source.remote.dto.GenresResponse
import com.example.podcastapp.data.data_source.remote.dto.LanguagesResponse
import com.example.podcastapp.data.data_source.remote.dto.PodcastDetailsDto
import com.example.podcastapp.data.data_source.remote.dto.PodcastSearchResponse
import com.example.podcastapp.data.data_source.remote.dto.RegionsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PodcastApi {

    @GET("best_podcasts")
    suspend fun getBestPodcasts(
        @Query("genre_id") genreId: Int? = 93,
        @Query("page") page: Int,
        @Query("region") region: String?,
        @Query("language") language: String?
    ): BestPodcastsResponse

    @GET("podcasts/{id}")
    suspend fun getPodcastDetails(
        @Path("id") id: String,
        @Query("next_episode_pub_date") nextEpisodePubDate: Long?
    ): PodcastDetailsDto

    @GET("genres")
    suspend fun getGenres(): GenresResponse

    @GET("regions")
    suspend fun getRegions(): RegionsResponse

    @GET("languages")
    suspend fun getLanguages(): LanguagesResponse

    @GET("search")
    suspend fun searchPodcasts(
        @Query("q") query: String,
        @Query("type") type: String = "podcast",
        @Query("language") language: String?,
        @Query("region") region: String?,
        @Query("genre_ids") genreId: Int?,
        @Query("offset") offset: Int
    ): PodcastSearchResponse
}