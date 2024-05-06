package com.example.news_app.presentation.onboarding
import androidx.annotation.DrawableRes
import com.example.news_app.R


data class page(
    val title:String,
    val description:String,
    @DrawableRes val image :Int ,
)

val pages = listOf(
    page(
        title = "Lorem Ipsum is simply dummy",
        description = "Lorem Ipsum is simply dummy text of the printing",
        image = R.drawable.onboarding1

    ),
    page(
        title = "Lorem Ipsum is simply dummy",
        description = "Lorem Ipsum is simply dummy text of the printing",
        image = R.drawable.onboarding2

    ),
    page(
        title = "Lorem Ipsum is simply dummy",
        description = "Lorem Ipsum is simply dummy text of the printing",
        image = R.drawable.onboarding3

    ),
)