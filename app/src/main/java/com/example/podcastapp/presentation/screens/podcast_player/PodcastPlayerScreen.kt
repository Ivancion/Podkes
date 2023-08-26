package com.example.podcastapp.presentation.screens.podcast_player

import android.content.ComponentName
import android.media.session.PlaybackState
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.podcastapp.R
import com.example.podcastapp.domain.model.Episode
import com.example.podcastapp.presentation.services.podcast_service.PodcastService
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun PodcastPlayerScreen(
    navigator: DestinationsNavigator,
    viewModel: PodcastPlayerViewModel = hiltViewModel()
) {

    val curPlayingPodcast by viewModel.curPlayingPodcast.collectAsState()
    val podcastDuration by viewModel.podcastDuration.collectAsState()
    val curPlayerPosition by viewModel.curPlayerPosition.collectAsState()
    val hasPreviousMediaItem by viewModel.hasPreviousMediaItem.collectAsState()
    val hasNextMediaItem by viewModel.hasNextMediaItem.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()

    var curPlayerPosInSec by remember {
        mutableStateOf(TimeUnit.MILLISECONDS.toSeconds(curPlayerPosition))
    }

    LaunchedEffect(key1 = curPlayerPosition) {
        curPlayerPosInSec = TimeUnit.MILLISECONDS.toSeconds(curPlayerPosition)
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 18.dp)
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(9f / 6f),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(curPlayingPodcast.mediaMetadata.artworkUri)
                    .crossfade(1000)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = curPlayingPodcast.mediaMetadata.title.toString(),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = curPlayingPodcast.mediaMetadata.artist.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = curPlayerPosInSec.toFloat(),
                valueRange = 0f..podcastDuration.toFloat(),
                onValueChange = { value ->
                    viewModel.setPlayerPosition(TimeUnit.SECONDS.toMillis(value.toLong()))
                },
                onValueChangeFinished = {
                    viewModel.seekTo(curPlayerPosition)
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
                Text(
                    text = dateFormat.format(curPlayerPosition),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = dateFormat.format(TimeUnit.SECONDS.toMillis(podcastDuration.toLong())),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(22.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    enabled = hasPreviousMediaItem == true,
                    onClick = {
                        viewModel.seekBack()
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp),
                        painter = painterResource(id = R.drawable.ic_skip_previous),
                        contentDescription = null,
                    )
                }
                Spacer(modifier = Modifier.width(24.dp))
                val interactionSource = remember {
                    MutableInteractionSource()
                }
                Icon(
                    modifier = Modifier
                        .size(56.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                        .clickable(
                            interactionSource,
                            indication = rememberRipple(
                                bounded = true,
                                radius = 28.dp
                            ),
                            onClick = {
                                viewModel.playPause()
                            }
                        ),
                    painter = painterResource(id = if(isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(24.dp))
                IconButton(
                    enabled = hasNextMediaItem == true,
                    onClick = {
                        viewModel.seekForward()
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp),
                        painter = painterResource(id = R.drawable.ic_skip_next),
                        contentDescription = null
                    )
                }
            }
        }
    }
}