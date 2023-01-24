package com.example.common.ui.screen

sealed class MainScreens(route: String) : BaseScreen(route) {

    object SplashScreen : MainScreens("splash_screen")
}