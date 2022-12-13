package com.example.programmingquotes.feature.quote.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.programmingquotes.core.common.createEmojiWithDecimalCode

@Composable
fun QuoteTopBar(
    modifier: Modifier = Modifier,
    emojiCode: Int,
    authorName: String
) {
    Text(
        modifier = Modifier
            .padding(
                vertical = 16.dp,
                horizontal = 8.dp
            )
            .then(modifier),
        text = "${createEmojiWithDecimalCode(emojiCode)} $authorName",
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.subtitle1,
        color = MaterialTheme.colors.onSurface,
        overflow = TextOverflow.Ellipsis
    )
}
