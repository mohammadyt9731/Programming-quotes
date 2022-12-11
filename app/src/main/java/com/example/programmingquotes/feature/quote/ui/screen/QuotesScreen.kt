package com.example.programmingquotes.feature.quote.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.programmingquotes.R
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.navigation.Screens
import com.example.programmingquotes.feature.quote.ui.component.QuoteListItem
import com.example.programmingquotes.feature.quote.ui.component.QuoteTopBar
import com.example.programmingquotes.feature.quote.ui.viewmodel.QuoteViewModel

@Composable
fun QuotesScreen(navHostController: NavHostController, authorName: String?) {
    val scaffoldState = rememberScaffoldState()
    val quoteViewModel: QuoteViewModel = hiltViewModel()
    val authorWithQuotes = quoteViewModel.authorWithQuotes.collectAsState().value
    val pageState = quoteViewModel.pageState.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = pageState) {
        when (pageState) {
            is ResultWrapper.NetworkError -> {
                if (authorWithQuotes.quotes.isEmpty())
                    scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.msg_no_internet))
            }
            is ResultWrapper.HttpError -> {
                pageState.message?.let {
                    scaffoldState.snackbarHostState.showSnackbar(pageState.message)
                }
            }
            else -> {}
        }
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
                emojiCode = authorWithQuotes.author.emoji,
                authorName = authorWithQuotes.author.name
            )
        }
    ) {
        if (pageState is ResultWrapper.Loading)
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        else {
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
}