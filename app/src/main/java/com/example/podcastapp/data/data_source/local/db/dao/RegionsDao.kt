package com.example.podcastapp.data.data_source.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.podcastapp.data.data_source.local.db.entity.RegionEntity

@Dao
interface RegionsDao {

    @Upsert
    suspend fun upsertRegions(regions: List<RegionEntity>)

    @Query("SELECT * FROM regions_table")
    suspend fun getRegions(): List<RegionEntity>
}