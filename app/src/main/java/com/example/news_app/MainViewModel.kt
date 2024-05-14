package com.example.news_app

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.news_app.domain.usecases.app_entry.AppEntryUseCase
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.news_app.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCase: AppEntryUseCase
) :ViewModel() {

     var splashCondition by mutableStateOf(true)
         private set

    var startDestination by mutableStateOf(Route.AppStatrNavigation.route)
        private set

    init {
        appEntryUseCase.readAppEntry().onEach {shouldStartFromHomeScreen ->
            if(shouldStartFromHomeScreen){
                startDestination = Route.NewsNavigation.route
            }else{
                startDestination = Route.AppStatrNavigation.route
            }
            delay(200)
            splashCondition = false

        }.launchIn(viewModelScope)

    }



}