package com.example.news_app.domain.repository

import com.example.news_app.domain.model.Article
import com.example.news_app.util.Resource
import kotlinx.coroutines.flow.Flow


interface NewsRepository {
    fun getNews(
        forceFetchFromRemote : Boolean ,
        sources: List<String> ,
        page : Int
    ): Flow<Resource<List<Article>>>

    suspend  fun searchNews(
        searchQuery: String,
        sources: List<String>
    ): Flow<Resource<List<Article>>>

    suspend fun upsertArticle(article: Article)

    suspend fun deleteArticle(article: Article)

    fun getArticles(): Flow<List<Article>>

    suspend fun getArticle(url : String) : Flow<Resource<Article>>
}