package com.example.news_app.presentation.search

import androidx.paging.LoadState
import com.example.news_app.domain.model.Article

data class SearchState(
    val isLoading : Boolean = false ,
    val searchQuery : String = "" ,
    val articles: List<Article>? = null ,
    val articleListPage : Int = 1 ,
    val error: LoadState.Error? = null
)
