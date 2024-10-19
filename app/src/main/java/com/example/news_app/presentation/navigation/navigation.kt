package com.example.news_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.news_app.presentation.onboarding.OnBoardingScreen
import com.example.news_app.presentation.onboarding.OnBoardingViewModel
import com.example.news_app.presentation.home.HomeScreen
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.navigation
import com.example.news_app.presentation.onboarding.OnBoardingScreen
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SetBarColor(color: Color){
    val systemUiController =rememberSystemUiController()
    LaunchedEffect(key1 = color) {
        systemUiController.setSystemBarsColor(color)
    }
}

@Composable
fun AppNavigation(
    startDestination : String
){
    val navController = rememberNavController()
    NavHost(
        navController = navController ,
        startDestination = startDestination
    ) {
        navigation(
            route = Screens.AppStartNavigation.route,
            startDestination = Screens.OnBoardingScreen.route
        ){
            composable(
                route = Screens.OnBoardingScreen.route
            ){
                val onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
                SetBarColor(MaterialTheme.colorScheme.onSecondary)
                OnBoardingScreen(onBoardingViewModel)
            }
        }
        navigation(
            route = Screens.NewsNavigation.route ,
            startDestination = Screens.NewsNavigatorScreen.route
        ){
            composable(
                route = Screens.NewsNavigatorScreen.route
            ){
                val homeViewModel : ArticleListViewModel = hiltViewModel()
                SetBarColor(MaterialTheme.colorScheme.onSecondary)
                HomeScreen(
                    homeViewModel ,
                    navigate = {}
                )
            }
        }
    }

}