package com.example.common.ui.screen

abstract class BaseScreen(val route:String) {

    fun withArg(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}