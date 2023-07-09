package com.example.podcastapp.data.data_source.local.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.podcastapp.data.data_source.local.db.entity.PodcastDetailsWithEpisodes
import com.example.podcastapp.data.data_source.local.db.entity.PodcastEntity

@Dao
interface PodcastDao {

    @Query("SELECT * FROM podcast_table")
    fun getAllPodcasts(): PagingSource<Int, PodcastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPodcasts(podcasts: List<PodcastEntity>)

    @Query("DELETE FROM podcast_table")
    suspend fun deleteAllPodcasts()

    @Query("SELECT * FROM podcast_table WHERE id = :id")
    fun getPodcastById(id: String): PodcastEntity

    @Transaction
    @Query("SELECT * FROM podcast_details_table")
    fun getPodcastsWithEpisodes(): List<PodcastDetailsWithEpisodes>
}