package com.example.programmingquotes.feature.authors.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.programmingquotes.feature.authors.ui.viewmodel.AuthorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun AuthorsScreen(
    navController: NavController,
    viewModel: AuthorViewModel
) {
    val scaffoldState = rememberScaffoldState()
    BottomSheet(
        scaffoldState = scaffoldState,
        viewModel = viewModel
    ) { bottomSheetState, scope ->
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = {
                AppBar()
            },
            content = {
                Body(
                    bottomSheetState = bottomSheetState,
                    scope = scope,
                    navController = navController,
                    scaffoldState = scaffoldState,
                    viewModel = viewModel
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
}

@Composable
private fun Body(
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: AuthorViewModel
) {
    val pageState = viewModel.pageState.collectAsState().value
    val context = LocalContext.current
    val pullRefreshState = rememberPullRefreshState(
        refreshing = pageState is ResultWrapper.Loading,
        onRefresh = { viewModel.getAuthors(true) }
    )

    LaunchedEffect(key1 = pageState) {
        if (pageState is Errors) {
            scaffoldState.snackbarHostState.showSnackbar(
                context.getMessageFromStringOrStringId(pageState.message)
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(state = pullRefreshState)
    ) {
        if (pageState is ResultWrapper.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                if (pageState is ResultWrapper.Success) {
                    items(pageState.data) { authorView ->
                        AuthorListItem(authorView) {
                            navController.navigate(Screens.QuotesScreen.withArg(authorView.name))
                        }
                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing = pageState is ResultWrapper.Loading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
