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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.programmingquotes.R
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.authors.ui.viewmodel.AuthorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun BottomSheet(
    scaffoldState: ScaffoldState,
    content: @Composable (bottomSheetState: ModalBottomSheetState, scope: CoroutineScope) -> Unit
) {

    val authorViewModel: AuthorViewModel = hiltViewModel()

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    LaunchedEffect(key1 = bottomSheetState.isVisible) {
        if (bottomSheetState.isVisible) {
            authorViewModel.startSensorManager()
        } else {
            authorViewModel.stopSensorManager()
        }
    }
    val scope = rememberCoroutineScope()

    BackHandler(enabled = bottomSheetState.isVisible) {
        scope.launch {
            bottomSheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        scrimColor = Color.Transparent,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetElevation = 15.dp,
        sheetContent = {
            val isShake = authorViewModel.isShakePhone.collectAsState()
            if (isShake.value) {
                SheetContentQuote(
                    authorViewModel = authorViewModel,
                    scope = scope,
                    scaffoldState = scaffoldState
                )
            } else {
                SheetContentShake()
            }
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
    authorViewModel: AuthorViewModel,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    val pageState = authorViewModel.pageStateBottomSheet.collectAsState().value
    LaunchedEffect(key1 = pageState) {
        if (pageState is ResultWrapper.Error) {
            pageState.message?.let { scaffoldState.snackbarHostState.showSnackbar(it) }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 200.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
    ) {

        if (pageState is ResultWrapper.Loading) {
            circularProgressIndicator()
        } else if (pageState is ResultWrapper.Success) {
            ContentQuote(
                title = "${pageState.data?.author}",
                content = "${pageState.data?.quote}"
            )
        }
    }
}

@Composable
private fun ContentQuote(
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


