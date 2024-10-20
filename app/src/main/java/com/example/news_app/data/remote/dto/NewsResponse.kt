package com.example.news_app.data.remote.dto


data class NewsResponse(
    val articles: List<ArticleDto>,
    val status: String,
    val totalResults: Int
)