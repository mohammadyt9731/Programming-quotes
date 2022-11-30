package com.example.programmingquotes.feature.quote.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
        text = "$emoji $authorName",
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.subtitle1,
        color = MaterialTheme.colors.onSurface,
        overflow = TextOverflow.Ellipsis
    )
}
