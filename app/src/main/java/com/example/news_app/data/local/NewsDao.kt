package com.example.news_app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface NewsDao {

    @Upsert
    suspend fun upsertArticleList(articleList : List<LocalArticle>)

    @Query("SELECT * FROM LocalArticle")
    fun getArticles(): List<LocalArticle>


    @Query("SELECT * FROM LocalArticle WHERE url=:url")
    suspend fun getArticleByUrl(url : String) : LocalArticle?

    @Delete
    suspend fun delete(localArticle: LocalArticle)



}