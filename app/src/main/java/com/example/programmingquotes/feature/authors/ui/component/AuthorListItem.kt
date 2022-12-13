package com.example.programmingquotes.feature.authors.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.programmingquotes.feature.authors.ui.model.AuthorView

@Composable
fun AuthorListItem(authorView: AuthorView, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(98.dp)
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .border(
                1.dp,
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colors.primary
            )
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Emoji(authorView.emoji)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            modifier = Modifier
                .weight(1f),
            text = authorView.name,
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        QuoteCountCircle(authorView.quoteCount)
    }

}

@Composable
private fun Emoji(emojiCode: Int) {
    Text(
        text = String(Character.toChars(emojiCode)),
        style = MaterialTheme.typography.subtitle1.copy(
            fontSize = 30.sp
        )
    )
}

@Composable
private fun QuoteCountCircle(quoteCount: Int) {
    Text(
        modifier = Modifier
            .size(26.dp)
            .clip(shape = CircleShape)
            .background(color = MaterialTheme.colors.primary),
        text = quoteCount.toString(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.button,
        color = MaterialTheme.colors.onPrimary
    )
}