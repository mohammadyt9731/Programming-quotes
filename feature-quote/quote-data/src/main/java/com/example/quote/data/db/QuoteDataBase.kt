package com.example.quote.data.db


interface QuoteDataBase {
    fun quoteDao(): QuoteDao
}