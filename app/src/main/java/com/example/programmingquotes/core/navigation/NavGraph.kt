package com.example.programmingquotes.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.programmingquotes.core.common.Constants

@Composable
fun NavGraph(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = Screens.SplashScreen.route) {

        composable(route = Screens.SplashScreen.route) {

        }
        composable(route = Screens.AuthorsScreen.route) {

        }
        composable(route = Screens.QuotesScreen.route,
            arguments = listOf(
                navArgument(name = Constants.AUTHOR_NAME_KEY) {
                    type = NavType.StringType
                }
            )
        ) {

        }
        composable(
            route = Screens.QuoteDetailScreen.route,
            arguments = listOf(
                navArgument(name = Constants.QUOTE_ID_KEY) {
                    type = NavType.IntType
                }
            )
        ) {

        }
    }
}