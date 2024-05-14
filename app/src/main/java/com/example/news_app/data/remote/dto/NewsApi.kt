package com.example.news_app.data.remote.dto

import com.example.news_app.util.constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET
    suspend fun getNews(
        @Query("sources") sources: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String= API_KEY
    ): NewsResponse

}