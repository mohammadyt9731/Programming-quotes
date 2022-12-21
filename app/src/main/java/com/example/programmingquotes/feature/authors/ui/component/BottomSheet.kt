package com.example.programmingquotes.feature.authors.ui.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.programmingquotes.R
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.authors.ui.viewmodel.AuthorViewModel
import com.example.programmingquotes.feature.quote.ui.model.QuoteView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun BottomSheet(
    viewModel: AuthorViewModel,
    bottomSheetState: ModalBottomSheetState,
    scaffoldState: ScaffoldState,
    pageStateBottomSheet: () -> ResultWrapper<QuoteView?>
) {

//    val isShakePhone = viewModel.isShakePhone.collectAsState()
    val pageState = pageStateBottomSheet()
    val scope = rememberCoroutineScope()


    LaunchedEffect(key1 = bottomSheetState.isVisible) {
        if (bottomSheetState.isVisible) {
            viewModel.startSensorManager()
        } else {
            viewModel.stopSensorManager()
        }
    }

    LaunchedEffect(key1 = pageState) {
        if (bottomSheetState.isVisible && pageState is ResultWrapper.Error) {
//            if (!isShakePhone.value) {
            bottomSheetState.hide()
            scaffoldState.snackbarHostState.showSnackbar(pageState.message).also {
                viewModel.resetPageStateBottomSheet()
//                }
            }
        }
    }

    BackHandler(enabled = bottomSheetState.isVisible) {
        scope.launch {
            bottomSheetState.hide()
        }
    }

    if (pageState is ResultWrapper.UnInitialize) {
        SheetContentNotShaken()
    } else {
        SheetContentShaken(
            pageStateBottomSheet = pageStateBottomSheet()
        )
    }
}

@Composable
private fun SheetContentNotShaken() {
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
private fun SheetContentShaken(
    pageStateBottomSheet: ResultWrapper<QuoteView?>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 200.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
    ) {
        if (pageStateBottomSheet is ResultWrapper.Loading) {
            circularProgressIndicator()
        } else if (pageStateBottomSheet is ResultWrapper.Success) {
            QuoteContent(
                title = "${pageStateBottomSheet.data?.author}",
                content = "${pageStateBottomSheet.data?.quote}"
            )
        }
    }
}

@Composable
private fun QuoteContent(
    title: String,
    content: String,
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

@Composable
private fun circularProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


