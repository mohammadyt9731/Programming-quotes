package com.example.programmingquotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.programmingquotes.core.navigation.NavGraph
import com.example.programmingquotes.core.ui.theme.ProgrammingQuotesTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //set status bar color
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)

            val navController = rememberNavController()
            MyApp {
                NavGraph(navHostController = navController)
            }
        }
    }
}

@Composable
private fun MyApp(
    content: @Composable () -> Unit
) {
    ProgrammingQuotesTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            content()
        }
    }
}