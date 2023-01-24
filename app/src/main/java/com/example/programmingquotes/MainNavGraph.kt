package com.example.programmingquotes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.common.ui.screen.MainScreens

const val MAIN_NAV_GRAPH = "main_nav_graph"
internal fun NavGraphBuilder.mainNavGraph(navHostController: NavHostController) {
    navigation(
        route = MAIN_NAV_GRAPH,
        startDestination = MainScreens.SplashScreen.route
    ) {
        composable(route = MainScreens.SplashScreen.route) {
            SplashScreen(navController = navHostController)
        }
    }
}