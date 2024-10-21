package com.example.news_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalArticle::class] ,
    version = 2 ,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract val newsDao : NewsDao
}