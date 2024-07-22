package com.example.exchangeapp.domain

import com.example.exchangeapp.data.network.response.ConvertCurrency
import com.example.exchangeapp.data.network.response.MultiCurrencyResponse
import com.example.exchangeapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface ApiRepository {
    suspend fun convertCurrency(from: String,to: String,amount: Int): Flow<ConvertCurrency>
    suspend fun multiConvertCurrency(from: String,to: String): Flow<MultiCurrencyResponse>
}