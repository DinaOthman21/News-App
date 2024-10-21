package com.example.news_app.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_app.domain.repository.NewsRepository
import com.example.news_app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val articleUrl =savedStateHandle.get<String>("articleUrl")

    private var _detailsScreenState = MutableStateFlow(DetailsScreenState())
    val detailsScreenState = _detailsScreenState.asStateFlow()

    init {
        getArticle(articleUrl ?: "")
    }

     fun getArticle(url: String) {
        viewModelScope.launch {
            _detailsScreenState.update {
                it.copy(isLoading = true)
            }
            newsRepository.getArticle(url = url).collectLatest { result ->
                when(result){
                    is Resource.Error -> {
                        _detailsScreenState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is Resource.Loading -> {
                        _detailsScreenState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { article ->
                            _detailsScreenState.update {
                                it.copy(article = article,isLoading = false)
                            }

                        }

                    }
                }
            }

        }
    }
}