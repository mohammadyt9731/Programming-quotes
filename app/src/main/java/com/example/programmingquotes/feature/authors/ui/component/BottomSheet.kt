package com.example.programmingquotes.feature.authors.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.programmingquotes.R
import kotlinx.coroutines.CoroutineScope

@ExperimentalMaterialApi
@Composable
fun BottomSheet(
    content: @Composable (bottomSheetState: ModalBottomSheetState, scope: CoroutineScope) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        scrimColor = Color.Transparent,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetElevation = 15.dp,
        sheetContent = {
            SheetContentShake()
//            SheetContentQuote(
//                title = "Jeff Sickel once said:",
//                content = "Nine women can't make a baby in one month."
//            )
        },
        content = { content(bottomSheetState, scope) }
    )
}

@Composable
private fun SheetContentShake() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .padding(top = 60.dp),
            painter = painterResource(id = R.drawable.ic_app),
            contentDescription = stringResource(id = R.string.content_desc_app),
        )
        Text(
            modifier = Modifier
                .padding(
                    top = 54.dp,
                    bottom = 32.dp,
                    start = 5.dp,
                    end = 5.dp,
                ),
            text = stringResource(id = R.string.label_shake_your_phone_to_see_magic),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
private fun SheetContentQuote(
    title: String,
    content: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            modifier = Modifier
                .padding(
                    top = 32.dp,
                    start = 32.dp
                ),
            text = title,
            style = MaterialTheme.typography.body1
        )
        Text(
            modifier = Modifier
                .padding(
                    top = 32.dp,
                    start = 24.dp,
                    end = 24.dp
                ),
            text = content,
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            modifier = Modifier
                .padding(
                    top = 32.dp,
                    bottom = 9.dp,
                    start = 32.dp
                ),
            text = stringResource(id = R.string.label_you_can_shake_again),
            style = MaterialTheme.typography.overline
        )
    }
}

