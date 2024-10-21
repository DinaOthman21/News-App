package com.example.news_app.domain.model


import androidx.room.Entity
import com.example.news_app.data.remote.dto.Source

@Entity
data class Article(
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)
