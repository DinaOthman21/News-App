package com.example.news_app.presentation.details

import com.example.news_app.domain.model.Article

sealed class DetailsScreenEvent {
     data class UpsertDeleteArticle(val article : Article) : DetailsScreenEvent()
     data object RemoveSideEffect : DetailsScreenEvent()
}