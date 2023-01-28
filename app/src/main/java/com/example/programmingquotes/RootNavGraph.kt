package com.example.programmingquotes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.author.ui.authorNavGraph
import com.example.quote.ui.quoteNavGraph

@Composable
internal fun RootNavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = MAIN_NAV_GRAPH) {
        mainNavGraph(navHostController)
        authorNavGraph(navHostController)
        quoteNavGraph(navHostController)
    }
}