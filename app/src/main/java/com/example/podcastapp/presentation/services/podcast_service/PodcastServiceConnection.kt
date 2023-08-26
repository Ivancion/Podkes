package com.example.podcastapp.presentation.services.podcast_service

import android.content.ComponentName
import android.content.Context
import android.media.session.PlaybackState
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.podcastapp.domain.model.Episode
import com.example.podcastapp.domain.model.PodcastDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PodcastServiceConnection(
    private val context: Context
) {
    private val _curPlayingPodcast = MutableStateFlow(MediaItem.EMPTY)
    val curPlayingPodcast = _curPlayingPodcast.asStateFlow()

    private val _playbackState = MutableStateFlow(PlaybackState.STATE_NONE)
    val playbackState = _playbackState.asStateFlow()

    private val _podcastDuration = MutableStateFlow(0)
    val podcastDuration = _podcastDuration.asStateFlow()

    private val _hasNextMediaItem = MutableStateFlow<Boolean?>(null)
    val hasNextMediaItem = _hasNextMediaItem.asStateFlow()

    private val _hasPreviousMediaItem = MutableStateFlow<Boolean?>(null)
    val hasPreviousMediaItem = _hasPreviousMediaItem.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    var controller: MediaController? = null
        private set
    private val sessionToken =
        SessionToken(context, ComponentName(context, PodcastService::class.java))
    private val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()

    private val podcastPlayerListener = object : Player.Listener {

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            mediaItem?.let {
                it.mediaMetadata.extras?.getInt("episode_duration")?.let { duration ->
                    _podcastDuration.value = duration
                }
                _curPlayingPodcast.value = mediaItem
                _hasNextMediaItem.value = controller?.hasNextMediaItem()
                _hasPreviousMediaItem.value = controller?.hasPreviousMediaItem()
            }
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            _playbackState.value = playbackState
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _isPlaying.value = isPlaying
        }
    }

    init {
        controllerFuture.addListener({
            controller = controllerFuture.get().apply {
                addListener(podcastPlayerListener)
            }
        }, ContextCompat.getMainExecutor(context))
    }
}