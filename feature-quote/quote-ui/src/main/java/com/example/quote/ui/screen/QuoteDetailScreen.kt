package com.example.quote_ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.base.ResultWrapper
import com.example.common.android.getMessageFromStringOrStringId
import com.example.common.android.openUri
import com.example.common.android.shareText
import com.example.common.ui.component.CustomButton
import com.example.quote_ui.R
import com.example.quote_ui.component.AutoResizeText
import com.example.quote_ui.component.QuoteTopBar
import com.example.quote_ui.viewmodel.QuoteDetailViewModel

@Composable
fun QuoteDetailScreen(
    index: Int,
    viewModel: QuoteDetailViewModel
) {

    val scaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState(initialPage = index)
    val context = LocalContext.current
    val viewState by viewModel.viewState.collectAsState()
    val authorWithQuotesState = viewState.authorWithQuotes

    LaunchedEffect(key1 = true) {
        viewModel.errorChannelFlow().collect {
            scaffoldState.snackbarHostState.showSnackbar(context.getMessageFromStringOrStringId(it))
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            if (authorWithQuotesState is ResultWrapper.Success) {
                QuoteTopBar(
                    emojiCode = authorWithQuotesState.data.authorEntity.emoji,
                    authorName = authorWithQuotesState.data.authorEntity.name
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (authorWithQuotesState is ResultWrapper.Success) {
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(bottom = 16.dp),
                    pageCount = authorWithQuotesState.data.quotes.size,
                    state = pagerState
                ) { page ->
                    AutoResizeText(
                        text = authorWithQuotesState.data.quotes[page].quote,
                        style = MaterialTheme.typography.h1
                    )
                }
                ButtonsSection(
                    onClickShare = {
                        context.shareText(text = authorWithQuotesState.data.quotes[index].quote)
                    },
                    onClickUri = {
                        context.openUri(uri = authorWithQuotesState.data.authorEntity.wikiUrl)
                    }
                )
            }
        }
    }
}

@Composable
private fun ButtonsSection(
    modifier: Modifier = Modifier,
    onClickShare: () -> Unit,
    onClickUri: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            .then(modifier)
    ) {
        CustomButton(
            modifier = Modifier.weight(1f),
            title = stringResource(id = R.string.label_share),
            onClick = onClickShare

        )
        Spacer(modifier = Modifier.width(16.dp))
        CustomButton(
            modifier = Modifier.weight(1f),
            title = stringResource(id = R.string.label_open_wiki_peida),
            onClick = onClickUri
        )
    }
}
