package com.jetbrains.dependoapp.presentation.screens.splash

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.dependoapp.AppPref.AppPreferences
import com.jetbrains.dependoapp.presentation.screens.Home.HomeScreen
import com.jetbrains.dependoapp.presentation.screens.Login.LoginScreen
import com.jetbrains.dependoapp.presentation.screens.mPinScreen.MPinScreen
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SplashScreen:Screen,KoinComponent {
    @Composable
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow
        val prefViewmodel :SplashViewModel by inject<SplashViewModel>()
        val appPreferences:AppPreferences by inject<AppPreferences>()
        Scaffold {
            SplashScreenContent(localNavigator, appPreferences = appPreferences,prefViewmodel)
        }
    }
}

@Composable
private fun SplashScreenContent(
    localNavigator: Navigator,
    appPreferences: AppPreferences,
    prefViewmodel: SplashViewModel
) {

    LaunchedEffect(Unit){
        prefViewmodel.navigateState.collect{
            when(it){
                is  NavigateEventsSplash.NavigateToLogin ->{
                    localNavigator.push(LoginScreen())
                }
                is NavigateEventsSplash.NavigateToHome->{
                    localNavigator.push(HomeScreen())
                }
                is NavigateEventsSplash.NavigateToMPINLogin->{
                    localNavigator.push(MPinScreen().copy(mobile = "9076664982"))
                }
                else -> {}
            }
        }

    }
}