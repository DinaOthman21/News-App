package com.example.news_app.presentation.details

import android.content.Context
import android.content.Intent
import android.net.Uri
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

    fun onBookmarkClick(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingArticle = newsRepository.getArticle(article.url)
            if (existingArticle == null) {
                upsertArticle(article)
            } else {
                deleteArticle(article)
            }
        }
    }

    fun onBrowsingClick(url: String , context: Context) {
        Intent(Intent.ACTION_VIEW).also {
            it.data = Uri.parse(url)
            if (it.resolveActivity(context.packageManager) != null) {
                context.startActivity(it)
            }
        }
    }

    fun onShareClick(url: String ,  context: Context) {
        Intent(Intent.ACTION_SEND).also {
            it.putExtra(Intent.EXTRA_TEXT, url)
            it.type = "text/plain"
            if (it.resolveActivity(context.packageManager) != null) {
                context.startActivity(it)
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