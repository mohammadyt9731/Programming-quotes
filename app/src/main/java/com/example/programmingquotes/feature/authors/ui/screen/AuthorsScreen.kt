package com.example.programmingquotes.feature.authors.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.programmingquotes.core.common.ErrorType
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.navigation.Screens
import com.example.programmingquotes.feature.authors.ui.component.AppBar
import com.example.programmingquotes.feature.authors.ui.component.AuthorListItem
import com.example.programmingquotes.feature.authors.ui.component.BottomSheet
import com.example.programmingquotes.feature.authors.ui.component.GenerateRandomButton
import com.example.programmingquotes.feature.authors.ui.viewmodel.AuthorViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AuthorsScreen(
    navController: NavController,
    viewModel: AuthorViewModel
) {
    val scaffoldState = rememberScaffoldState()
    BottomSheet(
        scaffoldState = scaffoldState
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
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Body(
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: AuthorViewModel
) {
    val authors by viewModel.authors.collectAsState()
    val pageState = viewModel.pageState.collectAsState().value
    val context = LocalContext.current
    val swipeState = rememberSwipeRefreshState(isRefreshing = false)

    LaunchedEffect(key1 = pageState) {
        if (pageState is ResultWrapper.Error) {
            if (authors.isEmpty() && pageState.type == ErrorType.NETWORK) {
                pageState.stringResId?.let {
                    scaffoldState.snackbarHostState.showSnackbar(context.getString(it))
                }
            } else {
                pageState.message?.let { scaffoldState.snackbarHostState.showSnackbar(it) }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (pageState is ResultWrapper.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            SwipeRefresh(
                state = swipeState,
                onRefresh = { viewModel.getAuthorsFromApiAndInsertToDb() }) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(authors) { author ->
                        AuthorListItem(author) {
                            navController.navigate(Screens.QuotesScreen.withArg(author.name))
                        }
                    }
                }
            }
        }
        GenerateRandomButton(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            onClick = {
                scope.launch {
                    bottomSheetState.show()
                }
            }
        )
    }
}
