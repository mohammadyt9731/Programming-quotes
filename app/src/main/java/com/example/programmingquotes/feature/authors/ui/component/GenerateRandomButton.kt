package com.example.programmingquotes.feature.authors.ui.component
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.programmingquotes.R

@Composable
fun GenerateRandomButton(
    modifier: Modifier = Modifier,
    onClick: ()-> Unit
) {
    Button(
        modifier = modifier
            .padding(bottom = 7.dp)
            .width(188.dp)
            .height(48.dp),
        onClick = { onClick.invoke() },
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = stringResource(id = R.string.label_generate_random),
            style = MaterialTheme.typography.button
        )
    }
}