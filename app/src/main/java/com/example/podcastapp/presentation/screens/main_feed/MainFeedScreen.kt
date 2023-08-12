package com.example.podcastapp.presentation.screens.main_feed

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.podcastapp.domain.model.Genre
import com.example.podcastapp.presentation.screens.common.PodcastListWidget
import com.example.podcastapp.presentation.screens.destinations.PodcastDetailsScreenDestination
import com.example.podcastapp.presentation.screens.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination()
@Composable
fun MainFeedScreen(
    navigator: DestinationsNavigator,
    viewModel: MainFeedViewModel = hiltViewModel()
) {
    val genres = viewModel.genres.collectAsState()
    val bestPodcasts = viewModel.bestPodcasts.collectAsLazyPagingItems()
    val selectedGenre = viewModel.selectedGenre.collectAsState()

    Log.d("MainFeedScreen", bestPodcasts.itemCount.toString())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Home",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = {
                        navigator.navigate(SearchScreenDestination())
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                items(genres.value) { genre ->
                    GenreItem(
                        genre = genre,
                        isSelected = selectedGenre.value == genre,
                        onGenreClick = {
                            viewModel.selectGenre(it)
                            bestPodcasts.refresh()
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(start = 24.dp),
                text = "Trending",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            PodcastListWidget(
                podcasts = bestPodcasts,
                onPodcastClick = {
                    navigator.navigate(PodcastDetailsScreenDestination(it.id))
                }
            )
        }
    }
}

@Composable
fun GenreItem(
    genre: Genre,
    isSelected: Boolean,
    onGenreClick: (Genre) -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Box(
        modifier = Modifier
            .background(
                color = if (!isSelected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.extraLarge
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    onGenreClick(genre)
                }
            )
            .padding(12.dp)
    ) {
        Text(
            text = genre.name,
            style = MaterialTheme.typography.bodyMedium,
            color = if(!isSelected) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onPrimary
        )
    }
}

fun <T : Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyGridItemScope.(value: T?) -> Unit
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index])
    }
}