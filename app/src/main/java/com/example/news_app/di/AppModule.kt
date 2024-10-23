package com.example.news_app.di

import android.app.Application
import androidx.room.Room
import com.example.news_app.data.local.NewsDao
import com.example.news_app.data.local.NewsDatabase
import com.example.news_app.data.manager.LocalUserManagerImpl
import com.example.news_app.data.remote.NewsApi
import com.example.news_app.data.repository.NewsRepositoryImpl
import com.example.news_app.domain.manager.LocalUserManager
import com.example.news_app.domain.repository.NewsRepository
import com.example.news_app.domain.usecases.app_entry.AppEntryUseCases
import com.example.news_app.domain.usecases.app_entry.ReadAppEntry
import com.example.news_app.domain.usecases.app_entry.SaveAppEntry
import com.example.news_app.util.Constants.BASE_URL
import com.example.news_app.util.Constants.NEWS_DATABASE_NAME
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
    ): LocalUserManager = LocalUserManagerImpl(application)


    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )


    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
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
        newsApi: NewsApi ,
        newsDao: NewsDao
    ): NewsRepository = NewsRepositoryImpl(newsApi , newsDao)


    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = NEWS_DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao

}