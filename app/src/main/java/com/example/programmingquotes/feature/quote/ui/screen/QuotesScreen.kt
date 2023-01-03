package com.example.programmingquotes.feature.quote.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.common.getMessageFromStringOrStringId
import com.example.programmingquotes.core.navigation.Screens
import com.example.programmingquotes.feature.quote.ui.action.QuoteAction
import com.example.programmingquotes.feature.quote.ui.component.QuoteListItem
import com.example.programmingquotes.feature.quote.ui.component.QuoteTopBar
import com.example.programmingquotes.feature.quote.ui.viewmodel.QuoteViewModel
import com.example.programmingquotes.feature.quote.ui.viewstate.QuoteViewState
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
internal fun QuotesScreen(
    navHostController: NavHostController,
    viewModel: QuoteViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val viewState by viewModel.viewState.collectAsState()
    val authorWithQuotesState = viewState.authorWithQuotesState
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewState.updateState is ResultWrapper.Loading,
        onRefresh = { viewModel.handleAction(QuoteAction.GetAuthorWithQuotesWhenRefresh) }
    )
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.errorChannel.receiveAsFlow().collect {
            scaffoldState.snackbarHostState.showSnackbar(context.getMessageFromStringOrStringId(it))
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            if (authorWithQuotesState is ResultWrapper.Success) {
                QuoteTopBar(
                    emojiCode = authorWithQuotesState.data.author.emoji,
                    authorName = authorWithQuotesState.data.author.name
                )
            }
        }
    ) {
        MainContent(
            pullRefreshState = { pullRefreshState },
            viewState = { viewState },
            navigateToDetail = { index, name ->
                navHostController.navigate(
                    Screens.QuoteDetailScreen.withArg(
                        "$index",
                        name
                    )
                )
            }
        )
    }
}

@Composable
private fun MainContent(
    pullRefreshState: () -> PullRefreshState,
    viewState: () -> QuoteViewState,
    navigateToDetail: (index: Int, name: String) -> Unit
) {
    val state = viewState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState())
    ) {
        if (state.updateState is ResultWrapper.Loading || state.authorWithQuotesState is ResultWrapper.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(all = 16.dp)
            ) {
                if (state.authorWithQuotesState is ResultWrapper.Success) {
                    items(state.authorWithQuotesState.data.quotes.size) { index ->
                        QuoteListItem(quote = state.authorWithQuotesState.data.quotes[index].quote) {
                            navigateToDetail(index, state.authorWithQuotesState.data.author.name)
                        }
                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing = state.updateState is ResultWrapper.Loading,
            state = pullRefreshState(),
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}