package com.example.news_app.presentation.bookmark

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.news_app.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _state = mutableStateOf(BookmarkState())
    val state : State<BookmarkState> = _state

    init {
        getArticles()
    }

    private fun getArticles(){
        newsRepository.getArticles().onEach {
            _state.value = _state.value.copy(articles = it.asReversed())
        }.launchIn(viewModelScope)
    }

}