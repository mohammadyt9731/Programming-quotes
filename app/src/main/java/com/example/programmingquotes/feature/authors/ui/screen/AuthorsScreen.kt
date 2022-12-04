package com.example.programmingquotes.feature.authors.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.programmingquotes.core.navigation.Screens
import com.example.programmingquotes.feature.authors.ui.component.AppBar
import com.example.programmingquotes.feature.authors.ui.component.AuthorListItem
import com.example.programmingquotes.feature.authors.ui.component.GenerateRandomButton
import com.example.programmingquotes.feature.authors.ui.viewmodel.AuthorViewModel

@Composable
fun AuthorsScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar()
        },
        content = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {
                    items(10) {
                        AuthorListItem() {
                            navController.navigate(Screens.QuotesScreen.withArg("Mohammad"))
                        }
                    }
                }
                GenerateRandomButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    onClick = {}
                )
            }
        }
    )

    val authorViewModel: AuthorViewModel = hiltViewModel()

   /* authorViewModel.insertAuthors(listOf(
        AuthorView(name = "Mohammad", wikiUrl = "aaa", quoteCount = 12, emoji = 123),
        AuthorView(name = "Mohammad2", wikiUrl = "asdf", quoteCount = 21, emoji = 123),
        AuthorView(name = "Mohammad123", wikiUrl = "fads", quoteCount = 3, emoji = 123),
        AuthorView(name = "Mohammad123123", wikiUrl = "g", quoteCount = 421, emoji = 123),
        AuthorView(name = "Mohammad123", wikiUrl = "d", quoteCount = 3, emoji = 234),
        AuthorView(name = "Mohammad123", wikiUrl = "d", quoteCount = 1, emoji = 234),
    ))*/
}