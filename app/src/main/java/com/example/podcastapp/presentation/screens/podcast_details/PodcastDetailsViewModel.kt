package com.example.podcastapp.presentation.screens.podcast_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.podcastapp.domain.repository.PodcastDetailsRepository
import com.example.podcastapp.domain.use_case.GetFormattedDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PodcastDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val podcastDetailsRepository: PodcastDetailsRepository,
    private val getFormattedDateUseCase: GetFormattedDateUseCase
): ViewModel() {

    private val _isShowFullDescription = MutableStateFlow(false)
    val isShowFullDescription = _isShowFullDescription.asStateFlow()

    val podcastEpisodes = podcastDetailsRepository.getPodcastEpisodes(checkNotNull(savedStateHandle["id"])).cachedIn(viewModelScope)

    val podcastDetails = podcastDetailsRepository.podcastDetails

    fun showFullDescription() {
        _isShowFullDescription.value = true
    }

    fun formatPublishDate(date: Long): String {
        return getFormattedDateUseCase(date, "dd MMM yyyy")
    }
}