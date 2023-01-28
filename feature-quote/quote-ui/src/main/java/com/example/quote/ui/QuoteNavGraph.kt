package com.example.quote.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.base.Constants
import com.example.common.ui.screen.QuotesScreens
import com.example.quote.ui.screen.QuoteDetailScreen
import com.example.quote.ui.screen.QuotesScreen
import com.example.quote.ui.viewmodel.QuoteDetailViewModel
import com.example.quote.ui.viewmodel.QuoteViewModel

fun NavGraphBuilder.quoteNavGraph(navHostController: NavHostController) {
    navigation(
        route = "quote_NavHost",
        startDestination = QuotesScreens.QuotesScreen.route
    ) {

        composable(route = QuotesScreens.QuotesScreen.route + "/{${Constants.AUTHOR_NAME_KEY}}",
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
            route = QuotesScreens.QuoteDetailScreen.route +
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