package com.example.news_app.presentation.onboarding

data class OnBoardingState(
    val currentPage: Int = 0,
    val isLastPage: Boolean = false,
    val isCompleted: Boolean = false
)