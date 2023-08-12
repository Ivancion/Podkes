package com.example.podcastapp.presentation.screens.search

sealed class SettingsMenuEvent {
    data class GenreSelected(val genre: String): SettingsMenuEvent()
    data class GenresMenuExpandedChange(val isExpanded: Boolean): SettingsMenuEvent()
    data class RegionSelected(val region: String): SettingsMenuEvent()
    data class RegionsMenuExpandedChange(val isExpanded: Boolean): SettingsMenuEvent()
    data class LanguageSelected(val language: String): SettingsMenuEvent()
    data class LanguagesMenuExpandedChange(val isExpanded: Boolean): SettingsMenuEvent()
    object SubmitSettings: SettingsMenuEvent()
}
