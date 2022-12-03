package com.example.programmingquotes.feature.quote.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.programmingquotes.R
import com.example.programmingquotes.feature.quote.ui.component.AutoResizeText
import com.example.programmingquotes.feature.quote.ui.component.QuoteTopBar
import com.example.programmingquotes.feature.quote.ui.component.RoundedButton
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun QuoteDetailScreen(id: Int? = 0) {
    val scaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState(initialPage = id?:0)

    val quotes = listOf(
        "Nine women can't make a baby in one month.",
        "Nine women can't make a baby in one month. Nine women can't make a baby in one month.",
        "Nine women can't make a baby in one month. Nine women can't make a baby in one month. Nine women can't make a baby in one month.",
        "Nine women can't make ",
        "Nine women can't make a one month.",
        "Ninn't mane month.",
        "Nine women can't make a baby in one month. Nine women can't make a baby in one month. Nine women can't make a baby in one month.Nine women can't make a baby in one month.Nine women can't make a baby in one month. Nine women can't make a baby in one month.Nine women can't make a baby in one month.Nine women can't make a baby in one month.Nine women can't make a baby in one month.Nine women can't make a baby in one month.Nine women can't make a baby in one month.",
        "Nine women can't make a baby in one month.",
        "Nine women can't make a baby in one month.",
        "Nine women can't make a baby in one month.",
        "Nine women can't make a baby in one month.",
        "Nine women can't make a baby in one month.",
        "Nine women can't make a baby in one month.",
        "Nine women can't make a baby in one month.",
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 16.dp),
        scaffoldState = scaffoldState,
        topBar = {
            QuoteTopBar(
                emojiCode = (128512..128580).random(),
                authorName = "Mohammad yazdi"
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp),
                count = quotes.size,
                state = pagerState
            ) { page ->
                AutoResizeText(
                    text = quotes[page],
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
                    //share quote
                }
                Spacer(modifier = Modifier.width(16.dp))
                RoundedButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.label_open_wiki_peida)
                ) {
                    //open wikipedia link
                }
            }
        }
    }
}