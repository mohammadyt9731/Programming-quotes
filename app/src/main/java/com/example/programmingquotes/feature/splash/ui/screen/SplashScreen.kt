package com.example.programmingquotes.feature.splash.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.common.Screens
import com.example.programmingquotes.R
import kotlinx.coroutines.delay

@Composable
internal fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(1000)
        navController.navigate(Screens.AuthorsScreen.route) {
            popUpTo(Screens.SplashScreen.route) {
                inclusive = true
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_app),
            contentDescription = stringResource(id = R.string.content_desc_app),
        )
    }
}