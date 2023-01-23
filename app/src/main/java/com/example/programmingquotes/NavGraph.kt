package com.example.programmingquotes

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.author.ui.AuthorViewModel
import com.example.author.ui.AuthorsScreen
import com.example.base.Constants
import com.example.common.ui.Screens
import com.example.quote.ui.screen.QuoteDetailScreen
import com.example.quote.ui.screen.QuotesScreen
import com.example.quote.ui.viewmodel.QuoteDetailViewModel
import com.example.quote.ui.viewmodel.QuoteViewModel

@Composable
internal fun NavGraph(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = Screens.SplashScreen.route
    ) {

        composable(route = Screens.SplashScreen.route) {
            SplashScreen(navController = navHostController)
        }
        composable(route = Screens.AuthorsScreen.route) {
            val viewModel: AuthorViewModel = hiltViewModel()
            AuthorsScreen(
                navController = navHostController,
                viewModel = viewModel
            )
        }
        composable(route = Screens.QuotesScreen.route + "/{${Constants.AUTHOR_NAME_KEY}}",
            arguments = listOf(
                navArgument(name = Constants.AUTHOR_NAME_KEY) {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel: QuoteViewModel = hiltViewModel()
            QuotesScreen(
                navHostController = navHostController,
                viewModel = viewModel
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
            val viewModel: QuoteDetailViewModel = hiltViewModel()
            QuoteDetailScreen(
                index = entry.arguments?.getInt(Constants.QUOTE_INDEX_KEY)!!,
                viewModel = viewModel
            )
        }
    }
}