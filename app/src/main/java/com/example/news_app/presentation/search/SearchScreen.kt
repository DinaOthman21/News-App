package com.example.news_app.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.news_app.presentation.Dimens.MediumPadding1
import com.example.news_app.presentation.common.ArticlesList
import com.example.news_app.presentation.common.SearchBar

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    navController : NavHostController
) {
    val searchState by searchViewModel.state
    Column(
        modifier = Modifier
            .padding(top = MediumPadding1, start = MediumPadding1, end = MediumPadding1)
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        SearchBar(
            text = searchState.searchQuery,
            readOnly = false,
            onValueChange = { query->
                searchViewModel.setSearchQuery(query) },
            onSearch = {
                searchViewModel.searchNews()
            }
        )
        Spacer(modifier = Modifier.height(MediumPadding1))
        searchState.articles?.let { articles ->
            ArticlesList(
                articles = articles,
                navController = navController ,
                isLoading = searchState.isLoading ,
                onPaginate = {
                    searchViewModel.paginateArticles()
                }
            )
        }

    }

}