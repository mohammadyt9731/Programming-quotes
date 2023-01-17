package com.example.quote_ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.common_android.safeClickable

@Composable
internal fun QuoteListItem(
    modifier: Modifier = Modifier,
    quote: String,
    onClick: () -> Unit
) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .border(
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary),
                shape = MaterialTheme.shapes.medium
            )
            .clip(shape = MaterialTheme.shapes.medium)
            .safeClickable { onClick() }
            .padding(all = 22.dp)
            .then(modifier),
        text = quote,
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.primary,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}