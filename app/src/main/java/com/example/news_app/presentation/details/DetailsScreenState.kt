package com.example.news_app.presentation.details

import com.example.news_app.domain.model.Article

data class DetailsScreenState(
    val isLoading: Boolean = false ,
    val article: Article? = null
)
