package com.example.programmingquotes.feature.authors.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.programmingquotes.core.navigation.Screens
import com.example.programmingquotes.feature.authors.ui.component.AppBar
import com.example.programmingquotes.feature.authors.ui.component.AuthorListItem
import com.example.programmingquotes.feature.authors.ui.component.BottomSheet
import com.example.programmingquotes.feature.authors.ui.component.GenerateRandomButton
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
                Content(
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
private fun Content(
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {
            items(10) {
                AuthorListItem {
                    navController.navigate(Screens.QuotesScreen.withArg("Mohammad"))
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