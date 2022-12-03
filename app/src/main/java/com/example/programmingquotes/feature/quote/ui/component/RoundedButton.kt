package com.example.programmingquotes.feature.quote.ui.component

import androidx.compose.foundation.background
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .clip(shape = MaterialTheme.shapes.small)
            .background(MaterialTheme.colors.primary)
            .then(modifier),
        onClick = { onClick() }) {
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.onPrimary
        )
    }
}
