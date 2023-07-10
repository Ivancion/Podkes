package com.example.podcastapp.presentation.screens.main_feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.podcastapp.domain.model.Genre
import com.example.podcastapp.domain.use_case.GetBestPodcastsUseCase
import com.example.podcastapp.domain.use_case.GetGenresUseCase
import com.example.podcastapp.domain.use_case.CacheGenresUseCase
import com.example.podcastapp.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFeedViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
    private val bestPodcastsUseCase: GetBestPodcastsUseCase
) : ViewModel() {

    private val _genres = MutableStateFlow(emptyList<Genre>())
    val genres = _genres.asStateFlow()

    private val _selectedGenre = MutableStateFlow<Genre?>(null)
    val selectedGenre = _selectedGenre.asStateFlow()

    val bestPodcasts = bestPodcastsUseCase(selectedGenre.value?.id).cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            when (val genres = getGenresUseCase()) {
                is Resource.Success -> {
                    Log.d("MainFeedViewModel", genres.data.toString())
                    _genres.value = genres.data
                }
                is Resource.Error -> {
                    Log.d("MainFeedViewModel", genres.exception)
                }
                Resource.LoadingState -> {}
            }
        }
    }
}
