package com.example.news_app.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.news_app.R
import com.example.news_app.presentation.Dimens.MediumPadding1
import com.example.news_app.presentation.common.SearchBar
import com.example.news_app.presentation.navigation.Screens
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import com.example.news_app.presentation.common.ArticlesList


@Composable
fun HomeScreen(
    articleListViewModel: ArticleListViewModel,
    navigate: (String) -> Unit
) {

    val articleState =articleListViewModel.articleListState.collectAsState().value
    val articles = articleState.articleList

    val titles by remember(articles) {
        derivedStateOf {
            if (articles.isNotEmpty()) {
                articles
                    .take(10)
                    .joinToString(separator = " \uD83D\uDFE5 ") { article ->
                        article.title
                    }
            } else {
                "No articles available"
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MediumPadding1)
            .statusBarsPadding()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(30.dp)
                .padding(horizontal = MediumPadding1)
        )
        Spacer(modifier = Modifier.height(MediumPadding1))
        SearchBar(
            modifier = Modifier.padding(horizontal = MediumPadding1),
            text = "",
            readOnly = true,
            onValueChange = {},
            onClick = {
                navigate(Screens.SearchScreen.route)
            },
            onSearch = {}
        )
        Spacer(modifier = Modifier.height(MediumPadding1))
        Text(
            text = titles,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = MediumPadding1)
                .basicMarquee(),
            fontSize = 12.sp,
            color = colorResource(id = R.color.placeholder)
        )
        Spacer(modifier = Modifier.height(MediumPadding1))
        ArticlesList(
            articles = articles ,
            onClick = {  navigate(Screens.DetailsScreen.route) } ,
            onPaginate ={ articleListViewModel.paginateArticles()} ,
            isLoading = articleState.isLoading
        )
    }

}
