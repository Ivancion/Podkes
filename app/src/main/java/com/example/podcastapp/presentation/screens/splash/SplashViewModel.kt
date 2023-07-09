package com.example.podcastapp.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.podcastapp.domain.use_case.LoadGenresUseCase
import com.example.podcastapp.domain.use_case.LoadLanguagesUseCase
import com.example.podcastapp.domain.use_case.LoadRegionsUseCase
import com.example.podcastapp.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loadGenresUseCase: LoadGenresUseCase,
    private val loadRegionsUseCase: LoadRegionsUseCase,
    private val loadLanguagesUseCase: LoadLanguagesUseCase
): ViewModel() {

    private val _screenState = MutableStateFlow<Resource<Unit>>(Resource.LoadingState)
    val screenState = _screenState.asStateFlow()

    fun loadConfigData() {
        viewModelScope.launch {
            when(val regionsResult = loadRegionsUseCase()) {
                is Resource.Success -> {
                    when(val genresResult = loadGenresUseCase()) {
                        is Resource.Success -> {
                            when(val languagesResult = loadLanguagesUseCase()) {
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