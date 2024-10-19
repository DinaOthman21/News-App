package com.example.news_app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface NewsDao {

    @Upsert
    suspend fun upsertArticleList(articleList : List<LocalArticle>)

    @Delete
    suspend fun delete(localArticle: LocalArticle)

    @Query("SELECT * FROM LocalArticle")
    fun getArticles(): List<LocalArticle>

}