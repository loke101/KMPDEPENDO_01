package com.jetbrains.dependoapp

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.jetbrains.dependoapp.AppPref.AppPrefViewmodel
import com.jetbrains.dependoapp.presentation.screens.Home.HomeScreen
import com.jetbrains.dependoapp.presentation.screens.Login.LoginScreen
import com.jetbrains.dependoapp.presentation.screens.splash.SplashScreen
import org.koin.core.component.KoinComponent

@Composable
fun App() {
    Scaffold {
        Navigator(SplashScreen()) {
            SlideTransition(it)
        }
    }
}
