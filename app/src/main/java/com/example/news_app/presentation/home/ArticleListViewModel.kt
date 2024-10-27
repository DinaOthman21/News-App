package com.example.news_app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
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
        getArticleList()
    }

    private fun getArticleList(loadMore: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            _articleListState.update {
                it.copy(isLoading = true)
            }
            if (!loadMore) {
                _articleListState.update {
                    it.copy(articleListPage = 1, articleList = emptyList())
                }
            }

            newsRepository.getNews(
                sources = listOf("bbc-news","abc-news","al-jazeera-english","TechCrunch") ,
                page = articleListState.value.articleListPage
            ).collectLatest { result ->
                when(result) {
                    is Resource.Error -> {
                        _articleListState.update {
                            it.copy(isLoading = false,
                                error = LoadState.Error(result.throwable ?: Throwable("Unknown Error")))
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
                                    articleList = if (loadMore) {
                                        articleListState.value.articleList + articleList
                                    } else {
                                        articleList.shuffled()
                                    } ,
                                    articleListPage = articleListState.value.articleListPage+1 ,
                                    isLoading = false
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