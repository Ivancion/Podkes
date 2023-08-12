package com.example.podcastapp.presentation.screens.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.podcastapp.presentation.screens.common.PodcastListWidget
import com.example.podcastapp.presentation.screens.search.components.SettingsMenu
import com.example.podcastapp.presentation.screens.destinations.PodcastDetailsScreenDestination
import com.example.podcastapp.presentation.screens.search.SettingsMenuEvent.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    searchViewModel: SearchViewModel = hiltViewModel()
) {

    val searchQuery = searchViewModel.searchQuery.collectAsState()
    val searchedPodcasts = searchViewModel.searchedPodcasts.collectAsLazyPagingItems()
    val genresDropdownMenuState = searchViewModel.genresDropdownMenuState.collectAsState()
    val regionsDropdownMenuState = searchViewModel.regionsDropdownMenuState.collectAsState()
    val languagesDropdownMenuState = searchViewModel.languagesDropdownMenuState.collectAsState()
    val settingsMenuIsVisible = searchViewModel.settingsMenuIsVisible.collectAsState()

    Scaffold(
        topBar = {
            SearchTopAppBar(
                text = searchQuery.value,
                onTextChange = searchViewModel::updateSearchQuery,
                onSearchClicked = searchViewModel::searchPodcasts,
                onSettingsClicked = searchViewModel::openSettingsMenu,
                onCloseClicked = { navigator.popBackStack() }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                SettingsMenu(
                    expanded = settingsMenuIsVisible.value,
                    onDismissRequest = searchViewModel::closeSettingsMenu,
                    genresDropdownMenuState = genresDropdownMenuState.value,
                    onGenreSelected = {
                        searchViewModel.onSettingsMenuEvent(GenreSelected(it))
                    },
                    onGenresMenuExpandedChange = {
                        searchViewModel.onSettingsMenuEvent(GenresMenuExpandedChange(it))
                    },
                    regionsDropdownMenuState = regionsDropdownMenuState.value,
                    onRegionSelected = {
                        searchViewModel.onSettingsMenuEvent(RegionSelected(it))
                    },
                    onRegionsMenuExpandedChange = {
                        searchViewModel.onSettingsMenuEvent(RegionsMenuExpandedChange(it))
                    },
                    languagesDropdownMenuState = languagesDropdownMenuState.value,
                    onLanguageSelected = {
                        searchViewModel.onSettingsMenuEvent(LanguageSelected(it))
                    },
                    onLanguagesMenuExpandedChange = {
                        searchViewModel.onSettingsMenuEvent(LanguagesMenuExpandedChange(it))
                    },
                    onSubmitSettings = {
                        searchViewModel.onSettingsMenuEvent(SubmitSettings)
                    }
                )
                PodcastListWidget(
                    podcasts = searchedPodcasts,
                    onPodcastClick = { podcast ->
                        navigator.navigate(PodcastDetailsScreenDestination(podcast.id))
                    }
                )
            }

        }
    )
}