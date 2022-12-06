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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.programmingquotes.core.navigation.Screens
import com.example.programmingquotes.feature.quote.ui.component.QuoteListItem
import com.example.programmingquotes.feature.quote.ui.component.QuoteTopBar
import com.example.programmingquotes.feature.quote.ui.viewmodel.QuoteViewModel

@Composable
fun QuotesScreen(navHostController: NavHostController, authorName: String?) {
    val scaffoldState = rememberScaffoldState()
    var emojiState by remember {
        mutableStateOf(0)
    }
    val quoteViewModel: QuoteViewModel = hiltViewModel()
    val authorWithQuotes = quoteViewModel.authorWithQuotes.collectAsState().value
    emojiState = authorWithQuotes.author.emoji
    if (authorName != null) {
        quoteViewModel.getQuotes(authorName)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 16.dp),
        scaffoldState = scaffoldState,
        topBar = {
            QuoteTopBar(
                modifier = Modifier.padding(horizontal = 8.dp),
                emojiCode = emojiState,
                authorName = authorName ?: ""
            )
        }
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(authorWithQuotes.quotes.size) { index ->
                QuoteListItem(quote = authorWithQuotes.quotes[index].quote) {
                    navHostController.navigate(
                        Screens.QuoteDetailScreen.withArg(
                            "$index",
                            "$authorName"
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}