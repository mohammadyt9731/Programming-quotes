package com.example.programmingquotes.feature.authors.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AuthorListItem() {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(98.dp),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.primary
        ),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
}

@Composable
private fun Emoji() {
    val code = (128512..128580).random()
    Text(
        text = String(Character.toChars(code)),
        style = MaterialTheme.typography.subtitle1
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