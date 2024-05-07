package com.example.news_app.di

import android.app.Application
import com.example.news_app.data.manager.localUserManagerImpl
import com.example.news_app.domain.manager.localUserManager
import com.example.news_app.domain.usecases.AppEntryUseCase
import com.example.news_app.domain.usecases.ReadAppEntry
import com.example.news_app.domain.usecases.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    )=AppEntryUseCase(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )


}