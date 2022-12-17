package com.example.programmingquotes.core.data.network

internal class ServerException(val code: Int, val messageError: String) : Exception()
