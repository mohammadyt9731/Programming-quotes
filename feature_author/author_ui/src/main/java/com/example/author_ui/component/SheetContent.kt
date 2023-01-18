package com.example.author_ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.author_ui.AuthorViewState
import com.example.author_ui.R
import com.example.common.ResultWrapper

@Composable
internal fun SheetContent(
    viewState: () -> AuthorViewState
) {
    val state = viewState()
    when (state.bottomSheet) {
        is ResultWrapper.Loading -> {
            ProgressBar()
        }
        is ResultWrapper.Success -> {
            state.bottomSheet.data?.let {
                SheetContentShaken(
                    authorName = it.author,
                    quote = it.en
                )
            }
        }
        else -> {
            SheetContentNotShaken()
        }
    }
}

@Composable
private fun ProgressBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SheetContentNotShaken() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .padding(top = 60.dp),
            painter = painterResource(id = R.drawable.ic_app),
            contentDescription = stringResource(id = R.string.content_desc_app),
        )
        Text(
            modifier = Modifier
                .padding(
                    top = 54.dp,
                    bottom = 32.dp,
                    start = 5.dp,
                    end = 5.dp,
                ),
            text = stringResource(id = R.string.label_shake_your_phone_to_see_magic),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
private fun SheetContentShaken(
    authorName: String,
    quote: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 200.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
    ) {
        QuoteContent(
            title = authorName,
            content = quote
        )
    }
}

@Composable
private fun QuoteContent(
    title: String,
    content: String,
) {
    Text(
        modifier = Modifier
            .padding(
                top = 32.dp,
                start = 32.dp
            ),
        text = title,
        style = MaterialTheme.typography.body1
    )
    Text(
        modifier = Modifier
            .padding(
                top = 32.dp,
                start = 24.dp,
                end = 24.dp
            ),
        text = content,
        style = MaterialTheme.typography.subtitle1
    )
    Text(
        modifier = Modifier
            .padding(
                top = 32.dp,
                bottom = 9.dp,
                start = 32.dp
            ),
        text = stringResource(id = R.string.label_you_can_shake_again),
        style = MaterialTheme.typography.overline
    )
}


