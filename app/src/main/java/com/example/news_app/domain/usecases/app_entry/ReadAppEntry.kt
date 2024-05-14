package com.example.news_app.domain.usecases.app_entry

import com.example.news_app.domain.manager.localUserManager
import kotlinx.coroutines.flow.Flow

 class ReadAppEntry(
    private val localUserManager: localUserManager
) {

    operator fun invoke(): Flow<Boolean> {
        return localUserManager.readAppEntry()
    }

}