package com.example.programmingquotes.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.programmingquotes.core.common.Constants
import com.example.programmingquotes.feature.authors.ui.screen.AuthorsScreen
import com.example.programmingquotes.feature.quote.ui.screen.QuoteDetailScreen
import com.example.programmingquotes.feature.quote.ui.screen.QuotesScreen
import com.example.programmingquotes.feature.splash.ui.screen.SplashScreen

@Composable
fun NavGraph(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = Screens.SplashScreen.route) {

        composable(route = Screens.SplashScreen.route) {
            SplashScreen(navController = navHostController)
        }
        composable(route = Screens.AuthorsScreen.route) {
            AuthorsScreen(navController = navHostController)
        }
        composable(route = Screens.QuotesScreen.route + "/{${Constants.AUTHOR_NAME_KEY}}",
            arguments = listOf(
                navArgument(name = Constants.AUTHOR_NAME_KEY) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            QuotesScreen(
                navHostController = navHostController,
                authorName = entry.arguments?.getString(Constants.AUTHOR_NAME_KEY)
            )
        }
        composable(
            route = Screens.QuoteDetailScreen.route +
                    "/{${Constants.QUOTE_INDEX_KEY}}" +
                    "/{${Constants.AUTHOR_NAME_KEY}}",
            arguments = listOf(

                navArgument(name = Constants.QUOTE_INDEX_KEY) {
                    type = NavType.IntType
                },
                navArgument(name = Constants.AUTHOR_NAME_KEY) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            QuoteDetailScreen(
                id = entry.arguments?.getInt(Constants.QUOTE_INDEX_KEY),
                authorName = entry.arguments?.getString(Constants.AUTHOR_NAME_KEY)
            )
        }
    }
}