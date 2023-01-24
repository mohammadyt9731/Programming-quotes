package com.example.quote.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.base.createEmojiWithDecimalCode

@Composable
internal fun QuoteTopBar(
    modifier: Modifier = Modifier,
    emojiCode: Int,
    authorName: String
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .then(modifier),
            text = "${createEmojiWithDecimalCode(emojiCode)} $authorName",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onSurface,
            overflow = TextOverflow.Ellipsis
        )
    }

}
