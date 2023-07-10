package com.example.podcastapp.data.data_source.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.podcastapp.data.data_source.local.db.entity.LanguageEntity

@Dao
interface LanguagesDao {

    @Upsert
    suspend fun upsertLanguages(languages: List<LanguageEntity>)

    @Query("SELECT * FROM languages_table")
    suspend fun getAllLanguages(): List<LanguageEntity>
}