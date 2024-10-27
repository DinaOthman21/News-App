package com.example.news_app.presentation.home

import androidx.paging.LoadState
import com.example.news_app.domain.model.Article

data class ArticleListState(
    val isLoading : Boolean = false ,
    val articleListPage : Int = 1 ,
    val articleList : List<Article> = emptyList() ,
    val error: LoadState.Error? = null
)
