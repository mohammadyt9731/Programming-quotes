package com.example.common_ui.component

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.common_android.safeOnClick

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String
) {
    Button(
        modifier = modifier,
        onClick = { safeOnClick(onClick) },
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.button
        )
    }
}