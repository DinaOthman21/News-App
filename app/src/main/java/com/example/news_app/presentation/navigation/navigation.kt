package com.example.news_app.presentation.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.news_app.presentation.onboarding.OnBoardingScreen
import com.example.news_app.presentation.onboarding.OnBoardingViewModel
import androidx.compose.ui.graphics.Color
import com.example.news_app.presentation.news_navigator.NewsNavigator
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
                SetBarColor(MaterialTheme.colorScheme.onSecondary)
                NewsNavigator()
            }
        }
    }

}