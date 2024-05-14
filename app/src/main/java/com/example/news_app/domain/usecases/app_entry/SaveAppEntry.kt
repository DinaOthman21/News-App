package com.example.news_app.domain.usecases.app_entry

import com.example.news_app.domain.manager.localUserManager

 class SaveAppEntry(
    private val localUserManager: localUserManager
) {

    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }


}