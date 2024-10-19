package com.example.news_app

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.news_app.domain.usecases.app_entry.AppEntryUseCases
import com.example.news_app.presentation.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class MainViewModel @Inject constructor(
    appEntryUseCases: AppEntryUseCases
) : ViewModel() {

    var splashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(Screens.AppStartNavigation.route)
        private set

    init {
        appEntryUseCases.readAppEntry().onEach { startFromHome ->
            startDestination = if (startFromHome){
                Screens.NewsNavigation.route
            } else{
                Screens.AppStartNavigation.route
            }
            delay(300)
            splashCondition = false

        }.launchIn(viewModelScope)
    }
}