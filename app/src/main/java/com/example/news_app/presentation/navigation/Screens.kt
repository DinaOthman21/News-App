package com.example.news_app.presentation.navigation

sealed class Screens (
    val route:String
) {
    data object OnBoardingScreen : Screens(route = "onBoardingScreen")
    data object HomeScreen : Screens(route="homeScreen")
    data object SearchScreen : Screens(route="searchScreen")
    data object BookMarkScreen : Screens(route="bookMarkScreen")
    data object DetailsScreen : Screens(route="detailsScreen")
    data object AppStartNavigation : Screens(route="appStartNavigation")
    data object NewsNavigation : Screens(route="newsNavigation")
    data object NewsNavigatorScreen : Screens(route="newsNavigatorScreen")
}