package com.example.podcastapp.presentation.screens.podcast_player

import android.media.session.PlaybackState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.podcastapp.other.Constants.UPDATE_PLAYER_POSITION_INTERVAL
import com.example.podcastapp.presentation.services.podcast_service.PodcastServiceConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastPlayerViewModel @Inject constructor(
    private val podcastServiceConnection: PodcastServiceConnection
) : ViewModel() {

    val curPlayingPodcast = podcastServiceConnection.curPlayingPodcast
    val playbackState = podcastServiceConnection.playbackState
    val podcastDuration = podcastServiceConnection.podcastDuration
    val hasPreviousMediaItem = podcastServiceConnection.hasPreviousMediaItem
    val hasNextMediaItem = podcastServiceConnection.hasNextMediaItem
    val isPlaying = podcastServiceConnection.isPlaying

    private val _curPlayerPosition = MutableStateFlow(0L)
    val curPlayerPosition = _curPlayerPosition.asStateFlow()

    private var isChangingSliderValue = false

    fun seekTo(position: Long) {
        podcastServiceConnection.controller?.seekTo(position)
        isChangingSliderValue = false
    }

    fun setPlayerPosition(value: Long) {
        isChangingSliderValue = true
        _curPlayerPosition.value = value
    }

    fun playPause() {
        if (isPlaying.value) {
            podcastServiceConnection.controller?.pause()
        } else {
            podcastServiceConnection.controller?.play()
        }
    }

    fun seekForward() {
        podcastServiceConnection.controller?.seekForward()
    }

    fun seekBack() {
        podcastServiceConnection.controller?.seekBack()
    }

    private fun updateCurrentPlayerPosition() {
        viewModelScope.launch {
            while (true) {
                val pos = podcastServiceConnection.controller?.currentPosition

                pos?.let {
                    if (!isChangingSliderValue) {
                        _curPlayerPosition.value = pos
                    }
                }
                delay(UPDATE_PLAYER_POSITION_INTERVAL)
            }
        }
    }

    init {
        updateCurrentPlayerPosition()
    }
}