package com.example.exchangeapp.domain.usecases

import com.example.exchangeapp.data.network.response.ConvertCurrency
import com.example.exchangeapp.data.network.response.MultiCurrencyResponse
import com.example.exchangeapp.domain.ApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MultiConvertCurrenciesUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {
    suspend operator fun invoke(from: String,to: String) : Flow<MultiCurrencyResponse> =
        apiRepository.multiConvertCurrency(from = from, to = to)
}