package com.example.exchangeapp.presentation.model

data class CurrencyPresentState(
    var currencies: String = "",
    var loadError: String = "",
    val isLoading: Boolean = false,
)
