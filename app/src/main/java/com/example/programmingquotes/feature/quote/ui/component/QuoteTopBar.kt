package com.example.programmingquotes.feature.quote.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun QuoteTopBar(
    modifier: Modifier = Modifier,
    emojiCode: Int,
    authorName: String
) {
    val emoji = String(Character.toChars(emojiCode))
    Text(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .then(modifier),
        text = "$emoji $authorName",
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.subtitle1,
        color = MaterialTheme.colors.onSurface,
        overflow = TextOverflow.Ellipsis
    )
}
