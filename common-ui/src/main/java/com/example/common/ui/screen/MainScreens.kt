package com.example.common.ui.screen

sealed class MainScreens(val route: String)  {

    object SplashScreen : MainScreens("splash_screen")
}