package com.example.news_app.presentation.details.components

sealed class DetailsEvent {
    object SaveArticle : DetailsEvent()
}