package com.example.programmingquotes.feature.quote.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.programmingquotes.R
import com.example.programmingquotes.core.common.openUri
import com.example.programmingquotes.core.common.shareText
import com.example.programmingquotes.core.ui.component.CustomButton
import com.example.programmingquotes.feature.quote.ui.component.AutoResizeText
import com.example.programmingquotes.feature.quote.ui.component.QuoteTopBar
import com.example.programmingquotes.feature.quote.ui.viewmodel.QuoteDetailViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@Composable
internal fun QuoteDetailScreen(
    index: Int,
    viewModel: QuoteDetailViewModel
) {

    val scaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState(initialPage = index)
    val context = LocalContext.current
    val viewState by viewModel.viewState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            QuoteTopBar(
                emojiCode = viewState.authorWithQuotes.author.emoji,
                authorName = viewState.authorWithQuotes.author.name
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 16.dp),
                count = viewState.authorWithQuotes.quotes.size,
                state = pagerState
            ) { page ->
                AutoResizeText(
                    text = viewState.authorWithQuotes.quotes[page].quote,
                    style = MaterialTheme.typography.h1
                )
            }
            ButtonsSection(
                onClickShare = {
                    context.shareText(text = viewState.authorWithQuotes.quotes[index].quote)
                },
                onClickUri = {
                    context.openUri(uri = viewState.authorWithQuotes.author.wikiUrl)
                }
            )
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
