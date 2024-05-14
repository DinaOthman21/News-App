package com.example.news_app.di

import android.app.Application
import com.example.news_app.data.manager.localUserManagerImpl
import com.example.news_app.data.remote.dto.NewsApi
import com.example.news_app.data.repository.NewsRepositoryImpl
import com.example.news_app.domain.manager.localUserManager
import com.example.news_app.domain.repository.NewsRepository
import com.example.news_app.domain.usecases.app_entry.AppEntryUseCase
import com.example.news_app.domain.usecases.app_entry.ReadAppEntry
import com.example.news_app.domain.usecases.app_entry.SaveAppEntry
import com.example.news_app.domain.usecases.news.GetNews
import com.example.news_app.domain.usecases.news.NewsUseCases
import com.example.news_app.util.constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ):localUserManager=localUserManagerImpl(application)


    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: localUserManager
    )= AppEntryUseCase(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun provideApiInstance(): NewsApi {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }


    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi
    ):NewsRepository=NewsRepositoryImpl(newsApi)

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository
    ):NewsUseCases {
        return NewsUseCases(
            getNews= GetNews(newsRepository)
        )
    }


}