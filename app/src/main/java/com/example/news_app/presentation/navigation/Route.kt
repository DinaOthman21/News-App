package com.example.news_app.presentation.navigation

sealed class Route(
    val route:String
) {
    object OnBoardingScreen:Route(route="onBoardingScreen")
    object HomeScreen:Route(route="homeScreen")
    object SearchScreen:Route(route="searchScreen")
    object BookMarkScreen:Route(route="bookMarkScreen")
    object DetailsScreen:Route(route="detailsScreen")
    object AppStatrNavigation:Route(route="appStatrNavigation")
    object NewsNavigation:Route(route="newsNavigation")
    object NewsNavigationScreen:Route(route="newsNavigationScreen")



}