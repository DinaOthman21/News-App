package com.example.news_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.example.news_app.domain.usecases.AppEntryUseCase
import com.example.news_app.presentation.navigation.Navigation
import com.example.news_app.presentation.onboarding.OnBoardingViewModel
import com.example.news_app.presentation.onboarding.OnBoardingScreen
import com.example.news_app.ui.theme.News_AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.splashCondition
            }
        }
        setContent {
            News_AppTheme {
             Box(modifier = Modifier.background(color= MaterialTheme.colorScheme.background) ){
                val startDestination =viewModel.startDestination
                 Navigation(startDestination = startDestination)

            }
        }
    }
}
}
