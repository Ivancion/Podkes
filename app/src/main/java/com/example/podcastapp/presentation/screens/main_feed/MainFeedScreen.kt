package com.example.podcastapp.presentation.screens.main_feed

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.podcastapp.data.data_source.remote.dto.BestPodcastDto
import com.example.podcastapp.domain.model.Podcast
import com.example.podcastapp.presentation.screens.components.SearchTextField
import com.example.podcastapp.presentation.screens.destinations.PodcastDetailsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun MainFeedScreen(
    navigator: DestinationsNavigator,
    viewModel: MainFeedViewModel = hiltViewModel()
) {
    val genres = viewModel.genres.collectAsState()
    val bestPodcasts = viewModel.bestPodcasts.collectAsLazyPagingItems()

    Log.d("MainFeedScreen", bestPodcasts.itemCount.toString())

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            hint = "Search",
            text = "",
            onTextChanged = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 24.dp)
        ) {
            items(genres.value) { genre ->
                SuggestionChip(
                    onClick = {},
                    label = {
                        Text(
                            text = genre.name,
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    enabled = false,
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    border = SuggestionChipDefaults.suggestionChipBorder(
                        disabledBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                        borderColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
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
        LazyVerticalGrid(
            modifier = Modifier.padding(24.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(bestPodcasts) { podcast ->
                podcast?.let {
                    PodcastItem(
                        podcast = it,
                        onPodcastClick = { podcast ->
                            navigator.navigate(PodcastDetailsScreenDestination(podcast.id))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PodcastItem(
    podcast: Podcast,
    onPodcastClick: (Podcast) -> Unit
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(podcast.image)
            .crossfade(true)
            .crossfade(durationMillis = 1000)
            .build()
    )
    Column(
        modifier = Modifier
            .clickable {
                onPodcastClick(podcast)
            },
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(4.dp)),
            painter = painter,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = podcast.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = podcast.publisher,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

fun <T: Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyGridItemScope.(value: T?) -> Unit
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index])
    }
}