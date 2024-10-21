package com.example.news_app.presentation.news_navigator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.news_app.R
import com.example.news_app.presentation.news_navigator.component.BottomNavigationItem
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.news_app.presentation.home.ArticleListViewModel
import com.example.news_app.presentation.home.HomeScreen
import com.example.news_app.presentation.navigation.Screens
import com.example.news_app.presentation.news_navigator.component.NewsBottomNavigation

@Composable
fun NewsNavigator() {

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark"),
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Screens.HomeScreen.route -> 0
            Screens.SearchScreen.route -> 1
            Screens.BookMarkScreen.route -> 2
            else -> 0
        }
    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Screens.HomeScreen.route ||
                backStackState?.destination?.route == Screens.SearchScreen.route ||
                backStackState?.destination?.route == Screens.BookMarkScreen.route
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selectedItem = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(
                                navController = navController,
                                route = Screens.HomeScreen.route
                            )

                            1 -> navigateToTab(
                                navController = navController,
                                route = Screens.SearchScreen.route
                            )

                            2 -> navigateToTab(
                                navController = navController,
                                route = Screens.BookMarkScreen.route
                            )
                        }
                    }
                )
            }

        }){  it ->
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Screens.HomeScreen.route) { _ ->
                val articleListViewModel : ArticleListViewModel = hiltViewModel()
                HomeScreen(
                    articleListViewModel = articleListViewModel ,
                    navigate = {}
                )
            }

            composable(route = Screens.SearchScreen.route) {
/*                val searchViewModel: SearchViewModel = hiltViewModel()
                SearchScreen(
                    searchViewModel = searchViewModel ,
                    navigateToDetails = {
                        navigateToDetails(
                            navController = navController ,
                            article =it
                        )
                    }
                )*/
            }

            composable(route = Screens.DetailsScreen.route) {
/*               val detailsViewModel: DetailsViewModel = hiltViewModel()
                if (detailsViewModel.sideEffect != null){
                    Toast.makeText(
                        LocalContext.current ,detailsViewModel.sideEffect ,Toast.LENGTH_SHORT
                    ).show()
                    detailsViewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                    ?.let { article ->
                        DetailsScreen(
                            article = article,
                            event = detailsViewModel::onEvent,
                            navigateUp = { navController.navigateUp() }
                        )
            }*/
            }

            composable(route = Screens.BookMarkScreen.route) {
 /*               val bookMarkViewModel: BookmarkViewModel = hiltViewModel()
                val state = bookMarkViewModel.state.value
                BookmarkScreen(
                    state = state,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController ,
                            article = article
                        )
                    }
                )*/
            }

        }

    }

}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screenRoute ->
            popUpTo(screenRoute) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}