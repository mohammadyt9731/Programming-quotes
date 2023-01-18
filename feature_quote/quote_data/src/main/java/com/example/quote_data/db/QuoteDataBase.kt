package com.example.quote_data.db

interface QuoteDataBase {
    fun quoteDao(): QuoteDao
}