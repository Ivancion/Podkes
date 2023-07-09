package com.example.podcastapp.data.data_source.remote

import com.example.podcastapp.data.data_source.remote.dto.BestPodcastsResponse
import com.example.podcastapp.data.data_source.remote.dto.GenreDto
import com.example.podcastapp.data.data_source.remote.dto.GenresResponse
import com.example.podcastapp.data.data_source.remote.dto.LanguagesResponse
import com.example.podcastapp.data.data_source.remote.dto.PodcastDetailsDto
import com.example.podcastapp.data.data_source.remote.dto.RegionsDto
import com.example.podcastapp.data.data_source.remote.dto.RegionsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.parameters

//class PodcastApiImpl(
//    private val client: HttpClient
//): PodcastApi {
//
//    override suspend fun getBestPodcasts(
//        genreId: Int?,
//        page: Int,
//        region: String?,
//        language: String?
//    ): BestPodcastsResponse {
//        return client.get {
//            url(ApiRoutes.BEST_PODCASTS)
//            header("X-ListenAPI-Key", "2e9a0504bed64edfb0f2c71400bcebcb")
//            parameters {
//                if(genreId != null) {
//                    append("genre_id", genreId.toString())
//                }
//                append("page", page.toString())
//                if(region != null) {
//                    append("region", region)
//                }
//                if(language != null) {
//                    append("language", language)
//                }
//            }
//        }.body()
//    }
//
//
//
//    override suspend fun getGenres(): GenresResponse {
//        return client.get {
//            url(ApiRoutes.GENRES)
//            parameter("top_level_only", "1")
//        }.body()
//    }
//
//    override suspend fun getRegions(): RegionsResponse {
//        return client.get {
//            url(ApiRoutes.REGIONS)
//        }.body()
//    }
//
//    override suspend fun getLanguages(): LanguagesResponse {
//        return client.get {
//            url(ApiRoutes.LANGUAGES)
//        }.body()
//    }
//}