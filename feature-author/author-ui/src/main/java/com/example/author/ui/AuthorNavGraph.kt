package com.example.author.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.common.ui.screen.AuthorsScreens


fun NavGraphBuilder.authorNavGraph(navHostController: NavHostController) {
    navigation(
        route = "author_nav_graph",
        startDestination = AuthorsScreens.AuthorsScreen.route
    ) {

        composable(route = AuthorsScreens.AuthorsScreen.route) {
            val viewModel: AuthorViewModel = hiltViewModel()
            AuthorsScreen(
                navController = navHostController,
                viewModel = viewModel
            )
        }
    }
}