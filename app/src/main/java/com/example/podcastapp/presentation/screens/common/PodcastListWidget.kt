package com.example.podcastapp.presentation.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.podcastapp.domain.model.Podcast

@Composable
fun PodcastListWidget(
    modifier: Modifier = Modifier,
    podcasts: LazyPagingItems<Podcast>,
    onPodcastClick: (Podcast) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        items(podcasts.itemCount) { index ->
            podcasts[index]?.let { podcast ->
                PodcastItem(
                    podcast = podcast,
                    onPodcastClick = {
                        onPodcastClick(it)
                    }
                )
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