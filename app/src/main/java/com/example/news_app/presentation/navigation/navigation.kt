package com.example.news_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.news_app.presentation.onboarding.OnBoardingScreen
import com.example.news_app.presentation.onboarding.OnBoardingViewModel
import androidx.compose.material3.Text

@Composable
fun Navigation(
    startDestination:String
){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
       navigation(
           route=Route.AppStatrNavigation.route,
           startDestination=Route.OnBoardingScreen.route
       ){
           composable(route=Route.OnBoardingScreen.route){

               val viewModel: OnBoardingViewModel = hiltViewModel()
               OnBoardingScreen (onEvent = viewModel::onEvent)

           }
       }

        navigation(
            route=Route.NewsNavigation.route,
            startDestination=Route.NewsNavigationScreen.route
        ){
            composable(route= Route.NewsNavigationScreen.route){
                Text(text="NewsNavigationScreen")
            }

        }
    }
}