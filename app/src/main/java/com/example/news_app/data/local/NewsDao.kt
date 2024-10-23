package com.example.news_app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertArticle(localArticle: LocalArticle)

    @Query("SELECT * FROM LocalArticle")
    fun getArticles(): Flow<List<LocalArticle>>


    @Query("SELECT * FROM LocalArticle WHERE url=:url")
    suspend fun getArticleByUrl(url : String) : LocalArticle?

    @Delete
    suspend fun delete(localArticle: LocalArticle)



}