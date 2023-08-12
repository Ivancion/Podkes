package com.example.podcastapp.data.repository

import com.example.podcastapp.data.data_source.local.db.PodcastDatabase
import com.example.podcastapp.domain.mappers.asGenre
import com.example.podcastapp.domain.mappers.asGenreEntity
import com.example.podcastapp.data.data_source.remote.PodcastApi
import com.example.podcastapp.domain.model.Genre
import com.example.podcastapp.domain.repository.GenresRepository
import com.example.podcastapp.other.Resource
import com.example.podcastapp.other.connection.InternetConnectionObserver

class GenresRepositoryImpl(
    private val api: PodcastApi,
    private val db: PodcastDatabase,
    private val connectionObserver: InternetConnectionObserver
) : GenresRepository, UtilDataRepositoryHelper<List<Genre>> by UtilDataRepositoryHelperImpl() {

    override suspend fun cacheGenres(): Resource<Unit> {
        return cacheData(
            getDataFromNetwork = ::getDataFromNetwork,
            saveDataIntoDb = ::saveDataIntoDb,
            haveInternetConnection = connectionObserver.isInternetAvailable(),
            haveCachedData = haveCachedData()
        )
    }

    override suspend fun getAllGenres(): List<Genre> {
        return getData(
            getDataFromDb = {
                db.genresDao().getAllGenres().map { it.asGenre() }
            }
        )
    }

    override suspend fun getGenresByIds(ids: List<Int>): List<Genre> {
        return db.genresDao().getGenresById(ids).map { it.asGenre() }
    }

    private suspend fun getDataFromNetwork(): List<Genre> {
        return api.getGenres().genres.map { it.asGenre() }
    }

    private suspend fun saveDataIntoDb(genres: List<Genre>) {
        db.genresDao().upsertGenres(genres.map { genre -> genre.asGenreEntity() })
    }

    private suspend fun haveCachedData(): Boolean {
        return db.genresDao().getAllGenres().isNotEmpty()
    }
}