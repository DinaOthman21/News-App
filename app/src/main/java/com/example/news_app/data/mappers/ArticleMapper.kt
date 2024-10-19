package com.example.news_app.data.mappers

import com.example.news_app.data.local.LocalArticle
import com.example.news_app.data.remote.dto.ArticleDto
import com.example.news_app.data.remote.dto.Source
import com.example.news_app.domain.model.Article


fun LocalArticle.toArticle(): Article {
    return Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        title = title,
        url = url,
        urlToImage = urlToImage,
        source = stringToSource(source)
    )
}


fun ArticleDto.toLocalArticle(): LocalArticle {
    return LocalArticle(
        author = author ?: "",
        content = content ?: "",
        description = description ?: "",
        publishedAt = publishedAt ?: "",
        title = title ?: "",
        url = url ?: "",
        urlToImage = urlToImage ?: "",
        source = sourceToString(source)
    )
}

fun sourceToString(source: Source?): String {
    return if (source != null) {
        "${source.id },${source.name }"
    } else {
        "Unknown,Unknown"
    }
}


fun stringToSource(sourceString: String): Source {
    val parts = sourceString.split(",")
    return Source(
        id = parts.getOrNull(0) ?: "",
        name = parts.getOrNull(1) ?: ""
    )
}


