package com.example.programmingquotes.core.ui.component
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String
) {
    Button(
        modifier = modifier,
        onClick = { onClick.invoke() },
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.button
        )
    }
}