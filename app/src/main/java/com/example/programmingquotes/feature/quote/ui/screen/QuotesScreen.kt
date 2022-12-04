package com.example.programmingquotes.feature.quote.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.programmingquotes.core.navigation.Screens
import com.example.programmingquotes.feature.quote.ui.component.QuoteListItem
import com.example.programmingquotes.feature.quote.ui.component.QuoteTopBar

@Composable
fun QuotesScreen(navHostController: NavHostController, authorName: String?) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 16.dp),
        scaffoldState = scaffoldState,
        topBar = {
            QuoteTopBar(
                modifier = Modifier.padding(horizontal = 8.dp),
                emojiCode = (128512..128580).random(),
                authorName = authorName ?: ""
            )
        }
    ) {
        val quotes = listOf(
            "Nine women can't make a baby in one month.",
            "Nine women can't make a baby in one month.",
            "Nine women can't make a baby in one month.",
            "Nine women can't make a baby in one month.",
            "Nine women can't make a baby in one month.",
            "Nine women can't make a baby in one month.",
            "Nine women can't make a baby in one month.",
            "Nine women can't make a baby in one month.",
            "Nine women can't make a baby in one month.",
            "Nine women can't make a baby in one month.",
            "Nine women can't make a baby in one month.",
            "Nine women can't make a baby in one month.",
            "Nine women can't make a baby in one month.",
            "Nine women can't make a baby in one month.",
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(quotes.size) { index ->
                QuoteListItem(quote = quotes[index]) {
                    navHostController.navigate(Screens.QuoteDetailScreen.withArg("1"))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}