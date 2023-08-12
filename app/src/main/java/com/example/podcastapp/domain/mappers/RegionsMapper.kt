package com.example.podcastapp.domain.mappers

import com.example.podcastapp.data.data_source.local.db.entity.RegionEntity
import com.example.podcastapp.data.data_source.remote.dto.RegionsDto
import com.example.podcastapp.domain.model.Region
import kotlin.reflect.full.declaredMemberProperties

fun RegionsDto.asRegions(): List<Region> {
    return this::class.declaredMemberProperties.map {
        Region(
            shortName = it.name,
            fullName = it.getter.call(this) as String
        )
    }
}

fun Region.asRegionEntity(): RegionEntity {
    return RegionEntity(
        shortName = shortName,
        fullName = fullName
    )
}

fun RegionEntity.asRegion(): Region {
    return Region(
        shortName = shortName,
        fullName = fullName
    )
}