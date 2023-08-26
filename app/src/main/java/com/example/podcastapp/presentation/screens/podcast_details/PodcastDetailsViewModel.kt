package com.example.podcastapp.presentation.screens.podcast_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.map
import com.example.podcastapp.domain.mappers.asMediaItem
import com.example.podcastapp.domain.model.Episode
import com.example.podcastapp.domain.model.Podcast
import com.example.podcastapp.domain.model.PodcastDetails
import com.example.podcastapp.domain.repository.PodcastDetailsRepository
import com.example.podcastapp.domain.use_case.GetFormattedDateUseCase
import com.example.podcastapp.presentation.services.podcast_service.PodcastServiceConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val podcastDetailsRepository: PodcastDetailsRepository,
    private val getFormattedDateUseCase: GetFormattedDateUseCase,
    private val podcastServiceConnection: PodcastServiceConnection
): ViewModel() {

    private val _isFullDescription = MutableStateFlow(false)
    val isFullDescription = _isFullDescription.asStateFlow()

    val podcastEpisodes = podcastDetailsRepository.getPodcastEpisodes(checkNotNull(savedStateHandle["id"])).cachedIn(viewModelScope)

    val podcastDetails = podcastDetailsRepository.podcastDetails

    fun toggleDescriptionVisibility() {
        _isFullDescription.value = !isFullDescription.value
    }

    fun formatPublishDate(date: Long): String {
        return getFormattedDateUseCase(date, "dd MMM yyyy")
    }

    fun setMediaItems(items: List<Episode>) {
        podcastDetails.value?.let { details ->
            podcastServiceConnection.controller?.setMediaItems(
                items.map {
                    it.asMediaItem(details)
                }
            )
        }
        podcastServiceConnection.controller?.let {
            it.prepare()
            it.play()
        }
    }
}