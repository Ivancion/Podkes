package com.example.podcastapp.presentation.screens.search

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.podcastapp.domain.model.Genre
import com.example.podcastapp.domain.model.Languages
import com.example.podcastapp.domain.model.Podcast
import com.example.podcastapp.domain.model.Region
import com.example.podcastapp.domain.use_case.GetGenresUseCase
import com.example.podcastapp.domain.use_case.GetLanguagesUseCase
import com.example.podcastapp.domain.use_case.GetRegionsUseCase
import com.example.podcastapp.domain.use_case.SearchPodcastsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.full.declaredMemberProperties

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchPodcastsUseCase: SearchPodcastsUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getRegionsUseCase: GetRegionsUseCase,
    private val getLanguagesUseCase: GetLanguagesUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchedPodcasts = MutableStateFlow<PagingData<Podcast>>(PagingData.empty())
    val searchedPodcasts = _searchedPodcasts.asStateFlow()

    private val _genresDropdownMenuState = MutableStateFlow(DropdownMenuState(selectedText = "None"))
    val genresDropdownMenuState = _genresDropdownMenuState.asStateFlow()

    private val _regionsDropdownMenuState = MutableStateFlow(DropdownMenuState())
    val regionsDropdownMenuState = _regionsDropdownMenuState.asStateFlow()

    private val _languagesDropdownMenuState = MutableStateFlow(DropdownMenuState())
    val languagesDropdownMenuState = _languagesDropdownMenuState.asStateFlow()

    private val _settingsMenuIsVisible = MutableStateFlow(false)
    val settingsMenuIsVisible = _settingsMenuIsVisible.asStateFlow()

    private var genres: List<Genre> = emptyList()
    private var regions: List<Region> = emptyList()
    private var languages: Languages = Languages(emptyList())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun openSettingsMenu() {
        _settingsMenuIsVisible.value = true
    }

    fun closeSettingsMenu() {
        _settingsMenuIsVisible.value = false
    }

    fun searchPodcasts(query: String) {
        viewModelScope.launch {
            searchPodcastsUseCase(
                query = query,
                genreId = genres.find { it.name == genresDropdownMenuState.value.selectedText }?.id,
                language = languages.names.find { it == languagesDropdownMenuState.value.selectedText },
                region = regions.find { it.fullName == regionsDropdownMenuState.value.selectedText }?.shortName
            )
                .cachedIn(viewModelScope)
                .collect {
                    _searchedPodcasts.value = it
                }
        }
    }

    fun onSettingsMenuEvent(event: SettingsMenuEvent) {
        when (event) {
            is SettingsMenuEvent.GenreSelected -> {
                _genresDropdownMenuState.value = genresDropdownMenuState.value.copy(
                    selectedText = event.genre
                )
            }
            is SettingsMenuEvent.GenresMenuExpandedChange -> {
                _genresDropdownMenuState.value = genresDropdownMenuState.value.copy(
                    expanded = event.isExpanded
                )
            }
            is SettingsMenuEvent.LanguageSelected -> {
                _languagesDropdownMenuState.value = languagesDropdownMenuState.value.copy(
                    selectedText = event.language
                )
            }
            is SettingsMenuEvent.LanguagesMenuExpandedChange -> {
                _languagesDropdownMenuState.value = languagesDropdownMenuState.value.copy(
                    expanded = event.isExpanded
                )
            }
            is SettingsMenuEvent.RegionSelected -> {
                _regionsDropdownMenuState.value = regionsDropdownMenuState.value.copy(
                    selectedText = event.region
                )
            }
            is SettingsMenuEvent.RegionsMenuExpandedChange -> {
                _regionsDropdownMenuState.value = regionsDropdownMenuState.value.copy(
                    expanded = event.isExpanded
                )
            }
            SettingsMenuEvent.SubmitSettings -> {
                _settingsMenuIsVisible.value = false
                if(searchQuery.value.isNotEmpty()) {
                    searchPodcasts(searchQuery.value)
                }
            }
        }
    }

    private fun setDropDownMenuStates() {
        viewModelScope.launch {
            val genresList = mutableListOf("None").apply {
                addAll(getGenres().map { it.name })
            }
            _genresDropdownMenuState.value = genresDropdownMenuState.value.copy(
                selectedText = genresList.first(),
                dropdownItemList = genresList
            )
            val regionsList = mutableListOf("None").apply {
                addAll(getRegions().map { it.fullName })
            }
            _regionsDropdownMenuState.value = regionsDropdownMenuState.value.copy(
                selectedText = regionsList.first(),
                dropdownItemList = regionsList
            )
            val languagesList = mutableListOf("None").apply {
                addAll(getLanguages().names)
            }
            _languagesDropdownMenuState.value = languagesDropdownMenuState.value.copy(
                selectedText = languagesList.first(),
                dropdownItemList = languagesList
            )
        }
    }

    private suspend fun getGenres(): List<Genre> {
        return getGenresUseCase().also {
            genres = it
        }
    }

    private suspend fun getRegions(): List<Region> {
        return getRegionsUseCase().also {
            regions = it
        }
    }

    private suspend fun getLanguages(): Languages {
        return getLanguagesUseCase().also {
            languages = it
        }
    }

    init {
        setDropDownMenuStates()
    }
}