package com.example.podcastapp.data.data_source.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.podcastapp.data.data_source.local.db.entity.PodcastRemoteKeys

@Dao
interface PodcastRemoteKeysDao {

    @Query("SELECT * FROM podcasts_remote_keys_table WHERE id = :id")
    suspend fun getRemoteKeys(id: String): PodcastRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKeys(remoteKeys: List<PodcastRemoteKeys>)

    @Query("DELETE FROM podcasts_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}