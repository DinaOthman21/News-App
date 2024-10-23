package com.example.news_app.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_app.domain.model.Article
import com.example.news_app.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel(){

    var sideEffect by mutableStateOf<String?>(null)
        private set

    fun upsertOrDeleteArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingArticle = newsRepository.getArticle(article.url)
            if (existingArticle == null) {
                upsertArticle(article)
            } else {
                deleteArticle(article)
            }
        }
    }

    fun removeSideEffect() {
        sideEffect = null
    }


    private suspend fun deleteArticle(article: Article) {
        newsRepository.deleteArticle(article = article)
        sideEffect = "Article Deleted"
    }

    private suspend fun upsertArticle(article: Article) {
        newsRepository.upsertArticle(article = article)
        sideEffect = "Article Saved"
    }


}