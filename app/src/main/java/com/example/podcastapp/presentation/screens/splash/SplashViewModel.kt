package com.example.podcastapp.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.podcastapp.domain.use_case.CacheGenresUseCase
import com.example.podcastapp.domain.use_case.CacheLanguagesUseCase
import com.example.podcastapp.domain.use_case.CacheRegionsUseCase
import com.example.podcastapp.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val cacheGenresUseCase: CacheGenresUseCase,
    private val cacheRegionsUseCase: CacheRegionsUseCase,
    private val cacheLanguagesUseCase: CacheLanguagesUseCase
): ViewModel() {

    private val _screenState = MutableStateFlow<Resource<Unit>>(Resource.LoadingState)
    val screenState = _screenState.asStateFlow()

    fun cacheUtilData() {
        viewModelScope.launch {
            when(val regionsResult = cacheRegionsUseCase()) {
                is Resource.Success -> {
                    when(val genresResult = cacheGenresUseCase()) {
                        is Resource.Success -> {
                            when(val languagesResult = cacheLanguagesUseCase()) {
                                is Resource.Success -> _screenState.value = Resource.Success(Unit)
                                is Resource.Error -> _screenState.value = Resource.Error(languagesResult.exception)
                                Resource.LoadingState -> Unit
                            }
                        }
                        is Resource.Error -> _screenState.value = Resource.Error(genresResult.exception)
                        Resource.LoadingState -> Unit
                    }
                }
                is Resource.Error -> _screenState.value = Resource.Error(regionsResult.exception)
                Resource.LoadingState -> Unit
            }
        }
    }
}