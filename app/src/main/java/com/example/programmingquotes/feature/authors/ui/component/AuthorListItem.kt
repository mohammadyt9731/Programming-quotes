package com.example.programmingquotes.feature.authors.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthorListItem(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(98.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
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
        Emoji()
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "Ken Thompson",
            style = MaterialTheme.typography.body1
        )
        Box(modifier = Modifier.weight(1f))
        CountCircle()
    }

}

@Composable
private fun Emoji() {
    val code = (128512..128580).random()
    Text(
        text = String(Character.toChars(code)),
        style = MaterialTheme.typography.subtitle1.copy(fontSize = 30.sp)
    )
}

@Composable
private fun CountCircle() {
    Surface(
        modifier = Modifier.size(26.dp),
        shape = CircleShape,
        color = MaterialTheme.colors.primary
    ) {
        Text(
            text = "12",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.button
        )
    }
}