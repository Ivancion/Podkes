package com.example.podcastapp.data.data_source.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.podcastapp.data.data_source.local.db.entity.GenreEntity

@Dao
interface GenresDao {

    @Upsert
    suspend fun upsertGenres(genres: List<GenreEntity>)

    @Query("SELECT * FROM genres_table")
    suspend fun getAllGenres(): List<GenreEntity>

    @Query("SELECT * FROM genres_table WHERE id IN (:genresIds)")
    suspend fun getGenresById(genresIds: List<Int>): List<GenreEntity>
}