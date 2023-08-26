package com.example.podcastapp.presentation.screens.podcast_details

import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.podcastapp.R
import com.example.podcastapp.domain.model.Episode
import com.example.podcastapp.domain.model.PodcastDetails
import com.example.podcastapp.presentation.screens.destinations.PodcastPlayerScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Destination
@Composable
fun PodcastDetailsScreen(
    navigator: DestinationsNavigator,
    id: String,
    viewModel: PodcastDetailsViewModel = hiltViewModel()
) {

    val episodes = viewModel.podcastEpisodes.collectAsLazyPagingItems()
    val podcastDetails = viewModel.podcastDetails.collectAsState()
    val isFullDescription = viewModel.isFullDescription.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(24.dp)
    ) {
        podcastDetails.value?.let { details ->
            item {
                PodcastDetailsSection(
                    podcastDetails = details,
                    isFullDescription = isFullDescription.value,
                    onShowMoreClick = {
                        viewModel.toggleDescriptionVisibility()
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
        items(episodes.itemCount) {
            episodes[it]?.let { episode ->
                EpisodeItem(
                    modifier = Modifier.clickable {
                        viewModel.setMediaItems(listOf(episode))
                        navigator.navigate(PodcastPlayerScreenDestination())
                    },
                    episode = episode,
                    formattedPubDate = viewModel::formatPublishDate,
                    isLastItem = it == episodes.itemCount - 1
                )
            }
        }
    }
}

@Composable
fun EpisodeItem(
    modifier: Modifier = Modifier,
    episode: Episode,
    formattedPubDate: (Long) -> String,
    isLastItem: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    modifier = Modifier.size(80.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(episode.image)
                        .crossfade(1000)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = episode.title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = Html.fromHtml(episode.description, FROM_HTML_MODE_LEGACY).toString(),
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row {
                        Text(
                            text = formattedPubDate(episode.pubDateMs),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(
                            text = "${episode.audioLengthSec} sec",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
        if (!isLastItem) {
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.onSurface)
            )
            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

@Composable
fun PodcastDetailsSection(
    podcastDetails: PodcastDetails,
    isFullDescription: Boolean,
    onShowMoreClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(150.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(podcastDetails.image)
                    .crossfade(1000)
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "Publisher: ${podcastDetails.publisher}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Total episodes: ${podcastDetails.totalEpisodes}",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Language: ${podcastDetails.language}",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Country: ${podcastDetails.country}",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.End),
                    painter = painterResource(id = R.drawable.download_icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = podcastDetails.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                text = Html.fromHtml(podcastDetails.description, FROM_HTML_MODE_LEGACY).toString(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                maxLines = if (!isFullDescription) 4 else Int.MAX_VALUE,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                modifier = Modifier
                    .clickable {
                        onShowMoreClick()
                    },
                text = if (isFullDescription) "Show Less" else "Show More",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}