package com.example.news_app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_app.domain.repository.NewsRepository
import com.example.news_app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private var _articleListState = MutableStateFlow(ArticleListState())
    val articleListState = _articleListState.asStateFlow()

    init {
        getArticleList(false)
    }

    private fun getArticleList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _articleListState.update {
                it.copy(isLoading = true)
            }
            newsRepository.getNews(
                forceFetchFromRemote = forceFetchFromRemote ,
                sources = listOf("bbc-news","abc-news","al-jazeera-english") ,
                page = articleListState.value.articleListPage
            ).collectLatest { result ->
                when(result) {
                    is Resource.Error -> {
                        _articleListState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is Resource.Loading -> {
                        _articleListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Resource.Success ->{
                        result.data?.let { articleList ->
                            _articleListState.update {
                                it.copy(
                                    articleList =articleListState.value.articleList +articleList.shuffled() ,
                                    articleListPage = articleListState.value.articleListPage+1
                                )
                            }

                        }
                    }
                }

            }

        }


    }

    fun paginateArticles() {
        getArticleList(true)
    }

}