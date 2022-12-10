package com.example.programmingquotes.feature.authors.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.programmingquotes.core.navigation.Screens
import com.example.programmingquotes.feature.authors.ui.component.AppBar
import com.example.programmingquotes.feature.authors.ui.component.AuthorListItem
import com.example.programmingquotes.feature.authors.ui.component.BottomSheet
import com.example.programmingquotes.feature.authors.ui.component.GenerateRandomButton
import com.example.programmingquotes.feature.authors.ui.viewmodel.AuthorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AuthorsScreen(navController: NavController) {
    BottomSheet { bottomSheetState, scope ->
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AppBar()
            },
            content = {
                Body(
                    bottomSheetState = bottomSheetState,
                    scope = scope,
                    navController = navController
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
    navController: NavController
) {
    val authorViewModel: AuthorViewModel = hiltViewModel()
    val authors = authorViewModel.authors.collectAsState()
    val isLoading = authorViewModel.isLoading.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (!isLoading.value) {
            LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {
                items(authors.value) { author ->
                    AuthorListItem(author) {
                        navController.navigate(Screens.QuotesScreen.withArg(author.name))
                    }
                }
            }
        } else {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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
