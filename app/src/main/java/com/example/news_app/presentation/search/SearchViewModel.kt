package com.example.news_app.presentation.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.news_app.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.news_app.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel(){

    private var _state = mutableStateOf(SearchState())
    val state : State<SearchState> = _state

    fun setSearchQuery(searchQuery: String) {
        _state.value = state.value.copy(searchQuery = searchQuery)
    }

    fun searchNews() {
        viewModelScope.launch(Dispatchers.IO) {
             newsRepository.searchNews(
                searchQuery = state.value.searchQuery,
                sources = listOf("bbc-news", "abc-news", "al-jazeera-english") ,
                page = state.value.articleListPage
            ).collectLatest { result ->
                when(result){
                    is Resource.Error -> {
                        _state.value=_state.value.copy(
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value=_state.value.copy(
                            isLoading = result.isLoading
                        )

                    }
                    is Resource.Success -> {
                        result.data?.let {  articleList ->
                            _state.value=_state.value.copy(
                                articles = articleList,
                                articleListPage = state.value.articleListPage+1
                            )
                        }
                    }
                }
            }
        }
    }
}