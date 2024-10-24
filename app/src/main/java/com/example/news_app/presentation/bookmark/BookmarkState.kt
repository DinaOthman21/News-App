package com.example.news_app.presentation.bookmark

import com.example.news_app.domain.model.Article

data class BookmarkState(
    val articles : List<Article> = emptyList()
)
