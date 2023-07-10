package com.example.podcastapp.data.data_source.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.podcastapp.data.data_source.local.db.entity.PodcastDetailsWithEpisodes

@Dao
interface PodcastDetailsDao {

    @Transaction
    @Query("SELECT * FROM podcast_details_table")
    suspend fun getPodcastsWithEpisodes(): List<PodcastDetailsWithEpisodes>

    @Transaction
    @Query("DELETE FROM podcast_details_table WHERE id = :id")
    suspend fun deletePodcastDetails(id: String)
}