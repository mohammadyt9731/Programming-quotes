package com.example.programmingquotes.feature.quote.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.programmingquotes.R
import com.example.programmingquotes.core.common.openUri
import com.example.programmingquotes.core.common.shareText
import com.example.programmingquotes.feature.quote.ui.component.AutoResizeText
import com.example.programmingquotes.feature.quote.ui.component.QuoteTopBar
import com.example.programmingquotes.feature.quote.ui.component.RoundedButton
import com.example.programmingquotes.feature.quote.ui.viewmodel.QuoteDetailViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun QuoteDetailScreen(
    index: Int? = 0,
    viewModel: QuoteDetailViewModel
) {

    val scaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState(initialPage = index ?: 0)
    val context = LocalContext.current
    val authorWithQuotes = viewModel.authorWithQuotes.collectAsState().value

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 16.dp),
        scaffoldState = scaffoldState,
        topBar = {
            QuoteTopBar(
                emojiCode = authorWithQuotes.author.emoji,
                authorName = authorWithQuotes.author.name
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp),
                count = authorWithQuotes.quotes.size,
                state = pagerState
            ) { page ->
                AutoResizeText(
                    text = authorWithQuotes.quotes[page].quote,
                    style = MaterialTheme.typography.h1
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                RoundedButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.label_share)
                ) {
                    index?.let {
                        context.shareText(text = authorWithQuotes.quotes[it].quote)
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                RoundedButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.label_open_wiki_peida)
                ) {
                    context.openUri(uri = authorWithQuotes.author.wikiUrl)
                }
            }
        }
    }
}
