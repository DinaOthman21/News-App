package com.example.news_app.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.news_app.domain.model.Article
import com.example.news_app.presentation.Dimens.ExtraSmallPadding2
import com.example.news_app.presentation.Dimens.MediumPadding1


@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClick: (Article) -> Unit,
    onPaginate: () -> Unit,
    isLoading: Boolean
) {
    if (articles.isEmpty() && !isLoading) {
        EmptyScreen()
    }
    else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MediumPadding1),
            contentPadding = PaddingValues(all = ExtraSmallPadding2)
        ) {
            itemsIndexed(articles) { index, article ->
                ArticleCard(article = article, onClick = { onClick(article) })

                if (index >= articles.size - 5 && !isLoading) {
                    onPaginate()
                }
            }

            if (isLoading) {
                item {
                    ShimmerEffect()
                }
            }
        }
    }
}

@Composable
private fun ShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding1)) {
        repeat(10) {
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }
    }
}