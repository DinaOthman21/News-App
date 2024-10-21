package com.example.news_app.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class LocalArticle(
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: String,
    val title: String,
    @PrimaryKey
    val url: String,
    val urlToImage: String
) : Parcelable
