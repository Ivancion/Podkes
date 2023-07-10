package com.example.podcastapp.data.mappers

import com.example.podcastapp.data.data_source.local.db.entity.LanguageEntity
import com.example.podcastapp.data.data_source.remote.dto.LanguagesResponse
import com.example.podcastapp.domain.model.Languages

fun LanguagesResponse.asLanguages(): Languages {
    return Languages(
        names = languages
    )
}

fun Languages.asLanguageEntities(): List<LanguageEntity> {
    return List(names.size) {
        LanguageEntity(names[it])
    }
}

fun List<LanguageEntity>.asLanguages(): Languages {
    return Languages(
        names = this.map { it.name }
    )
}