package com.example.news_app.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import com.example.news_app.domain.manager.localUserManager
import kotlinx.coroutines.flow.Flow
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.news_app.util.constants
import com.example.news_app.util.constants.USER_SETTINGS
import kotlinx.coroutines.flow.map


class localUserManagerImpl(
    private val context: Context
) : localUserManager {
    override suspend fun saveAppEntry() {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.APP_ENTRY]=true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map {preferences ->
            preferences[PreferenceKeys.APP_ENTRY] ?: false
        }
    }


    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

    private object PreferenceKeys {
        val APP_ENTRY = booleanPreferencesKey(name = constants.APP_ENTRY )
    }


}



