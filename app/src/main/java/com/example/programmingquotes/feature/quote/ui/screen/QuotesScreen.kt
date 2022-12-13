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
import androidx.navigation.NavHostController
import com.example.programmingquotes.core.common.ErrorType
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.navigation.Screens
import com.example.programmingquotes.feature.quote.ui.component.QuoteListItem
import com.example.programmingquotes.feature.quote.ui.component.QuoteTopBar
import com.example.programmingquotes.feature.quote.ui.model.AuthorWithQuotesView
import com.example.programmingquotes.feature.quote.ui.viewmodel.QuoteViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun QuotesScreen(
    navHostController: NavHostController,
    viewModel: QuoteViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val authorWithQuotes = viewModel.authorWithQuotes.collectAsState().value
    val pageState = viewModel.pageState.collectAsState().value
    val swipeRefreshState =
        rememberSwipeRefreshState(isRefreshing = false)
    val context = LocalContext.current

    LaunchedEffect(key1 = pageState) {
        if (pageState is ResultWrapper.Error) {
            if (authorWithQuotes.quotes.isEmpty() && pageState.type == ErrorType.NETWORK) {
                pageState.stringResId?.let {
                    scaffoldState.snackbarHostState.showSnackbar(context.getString(it))
                }
            } else {
                pageState.message?.let { scaffoldState.snackbarHostState.showSnackbar(it) }
            }
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
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        else {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    viewModel.fetchAuthorQuotesFromApiAndInsertToDb()
                }) {
                ContentLazyColumn(authorWithQuotes, navHostController)
            }
        }
    }
}

@Composable
private fun ContentLazyColumn(
    authorWithQuotes: AuthorWithQuotesView,
    navHostController: NavHostController
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(authorWithQuotes.quotes.size) { index ->
            QuoteListItem(quote = authorWithQuotes.quotes[index].quote) {
                navHostController.navigate(
                    Screens.QuoteDetailScreen.withArg(
                        "$index",
                        authorWithQuotes.author.name
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}