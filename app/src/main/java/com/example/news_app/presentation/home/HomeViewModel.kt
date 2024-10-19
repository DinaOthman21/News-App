package com.example.news_app.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   private val newsUseCases: NewsUseCases
): ViewModel() {

    val news = newsUseCases.getNews(
        sources = listOf("bbc-news","abc-news","al-jazeera-english")
    ).cachedIn(viewModelScope)


    init {
        viewModelScope.launch {
            news.catch { e ->
                Log.e("HomeViewModel", "Error fetching news articles", e)
            }.collectLatest { pagingData ->
                Log.d("HomeViewModel", "Fetched news articles: $pagingData")
            }
        }
    }



}