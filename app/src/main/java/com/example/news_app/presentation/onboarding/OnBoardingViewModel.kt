package com.example.news_app.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_app.domain.usecases.app_entry.AppEntryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val appEntryUseCases : AppEntryUseCases
) : ViewModel() {

    private val _onBoardingState = MutableStateFlow(OnBoardingState())
    val onBoardingState: StateFlow<OnBoardingState> = _onBoardingState


    fun onNext() {
        _onBoardingState.update { state ->
            val newPage = state.currentPage + 1
            state.copy(
                currentPage = newPage,
                isLastPage = newPage == 2
            )
        }
    }

    fun onBack() {
        _onBoardingState.update { state ->
            val newPage = state.currentPage - 1
            state.copy(
                currentPage = newPage,
                isLastPage = false
            )
        }
    }

    fun completeOnBoarding() {
        viewModelScope.launch {
            appEntryUseCases.saveAppEntry()
        }

        _onBoardingState.update { state ->
            state.copy(isCompleted = true)
        }
    }



}