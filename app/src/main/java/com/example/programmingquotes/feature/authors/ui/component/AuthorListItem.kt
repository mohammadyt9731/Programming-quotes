package com.example.programmingquotes.feature.authors.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.programmingquotes.core.common.createEmojiWithDecimalCode
import com.example.programmingquotes.core.common.safeClickable
import com.example.programmingquotes.feature.authors.data.db.entity.AuthorEntity

@Composable
internal fun AuthorListItem(author: AuthorEntity, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(98.dp)
            .border(
                1.dp,
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colors.primary
            )
            .clip(shape = MaterialTheme.shapes.medium)
            .safeClickable { onClick() }
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Emoji(author.emoji)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            modifier = Modifier
                .weight(1f),
            text = author.name,
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        QuoteCountCircle(author.quoteCount)
    }

}

@Composable
private fun Emoji(emojiCode: Int) {
    Text(
        text = createEmojiWithDecimalCode(emojiCode),
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