package com.example.podcastapp.data.data_source.mappers

import com.example.podcastapp.data.data_source.remote.dto.LanguagesResponse
import com.example.podcastapp.domain.model.Languages

fun LanguagesResponse.asLanguages(): Languages {
    return Languages(
        names = languages
    )
}