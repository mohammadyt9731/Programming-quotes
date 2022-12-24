package com.example.programmingquotes.feature.quote.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.common.getMessageFromStringOrStringId
import com.example.programmingquotes.core.navigation.Screens
import com.example.programmingquotes.feature.quote.ui.component.QuoteListItem
import com.example.programmingquotes.feature.quote.ui.component.QuoteTopBar
import com.example.programmingquotes.feature.quote.ui.viewmodel.QuoteViewModel

@Composable
internal fun QuotesScreen(
    navHostController: NavHostController,
    viewModel: QuoteViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val pageState = viewModel.pageState.collectAsState().value
    val context = LocalContext.current
    val pullRefreshState = rememberPullRefreshState(
        refreshing = pageState is ResultWrapper.Loading,
        onRefresh = { viewModel.getAuthorWithQuotes(isRefresh = true) }
    )

    LaunchedEffect(key1 = pageState) {
        if (pageState is ResultWrapper.Error) {
            scaffoldState.snackbarHostState.showSnackbar(
                context.getMessageFromStringOrStringId(pageState.errors.message)
            )
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        scaffoldState = scaffoldState,
        topBar = {
            if (pageState is ResultWrapper.Success) {
                QuoteTopBar(
                    emojiCode = pageState.data.author.emoji,
                    authorName = pageState.data.author.name
                )
            }
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            if (pageState is ResultWrapper.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (pageState is ResultWrapper.Success) {
                        items(pageState.data.quotes.size) { index ->
                            QuoteListItem(quote = pageState.data.quotes[index].quote) {
                                navHostController.navigate(
                                    Screens.QuoteDetailScreen.withArg(
                                        "$index",
                                        pageState.data.author.name
                                    )
                                )
                            }
                        }
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = pageState is ResultWrapper.Loading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}