package com.example.programmingquotes.feature.authors.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.programmingquotes.R
import com.example.programmingquotes.core.common.Errors
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.common.getMessageFromStringOrStringId
import com.example.programmingquotes.core.navigation.Screens
import com.example.programmingquotes.core.ui.component.CustomButton
import com.example.programmingquotes.feature.authors.ui.component.AppBar
import com.example.programmingquotes.feature.authors.ui.component.AuthorListItem
import com.example.programmingquotes.feature.authors.ui.component.BottomSheet
import com.example.programmingquotes.feature.authors.ui.model.AuthorView
import com.example.programmingquotes.feature.authors.ui.viewmodel.AuthorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun AuthorsScreen(
    navController: NavController,
    viewModel: AuthorViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val pageStateBottomSheet by viewModel.pageStateBottomSheet.collectAsState()
    val pageState by viewModel.pageState.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = pageState is ResultWrapper.Loading,
        onRefresh = { viewModel.getAuthors(true) }
    )
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = bottomSheetState,
        scrimColor = Color.Transparent,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetElevation = 15.dp,
        sheetContent = {
            BottomSheet(
                viewModel = viewModel,
                pageStateBottomSheet = { pageStateBottomSheet },
                scaffoldState = scaffoldState,
                bottomSheetState = bottomSheetState
            )
        },
        content = {
            MainContent(
                scaffoldState = scaffoldState,
                navController = navController,
                pullRefreshState = pullRefreshState,
                pageState = pageState,
                scope = scope,
                bottomSheetState = bottomSheetState
            )
        },
    )
}

@Composable
private fun MainContent(
    scaffoldState: ScaffoldState,
    navController: NavController,
    pullRefreshState: PullRefreshState,
    pageState: ResultWrapper<List<AuthorView>>,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            AppBar()
        },
        content = { padding ->
            Body(
                navController = navController,
                scaffoldState = scaffoldState,
                paddingValues = padding,
                pullRefreshState = pullRefreshState,
                pageState = { pageState }
            )
        },
        floatingActionButton = {
            CustomButton(
                modifier = Modifier
                    .width(188.dp),
                onClick = {
                    scope.launch {
                        bottomSheetState.show()
                    }
                },
                title = stringResource(id = R.string.label_generate_random)
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    )
}

@Composable
private fun Body(
    navController: NavController,
    scaffoldState: ScaffoldState,
    paddingValues: PaddingValues,
    pullRefreshState: PullRefreshState,
    pageState: () -> ResultWrapper<List<AuthorView>>
) {
    val context = LocalContext.current
    val state = pageState()

    LaunchedEffect(key1 = state) {
        if (state is Errors) {
            scaffoldState.snackbarHostState.showSnackbar(
                context.getMessageFromStringOrStringId(state.message)
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(state = pullRefreshState)
            .padding(paddingValues)
    ) {
        if (state is ResultWrapper.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                if (state is ResultWrapper.Success) {
                    items(state.data) { authorView ->
                        AuthorListItem(authorView) {
                            navController.navigate(Screens.QuotesScreen.withArg(authorView.name))
                        }
                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing = state is ResultWrapper.Loading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
