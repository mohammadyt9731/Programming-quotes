package com.example.programmingquotes.feature.quote.ui.component

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
internal fun AutoResizeText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle
) {
    var textStyle by remember { mutableStateOf(style.copy(fontSize = 100.sp)) }
    var readyToDraw by remember { mutableStateOf(false) }
    Text(
        modifier = modifier
            .drawWithContent {
                if (readyToDraw) {
                    drawContent()
                }
            },
        text = text,
        style = textStyle,
        overflow = TextOverflow.Clip,
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.didOverflowHeight) {
                textStyle = textStyle.copy(fontSize = textStyle.fontSize * 0.9)
            } else {
                readyToDraw = true
            }
        }
    )
}
