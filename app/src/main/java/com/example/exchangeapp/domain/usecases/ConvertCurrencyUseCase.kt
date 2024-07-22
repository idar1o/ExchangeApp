package com.example.exchangeapp.domain.usecases

import com.example.exchangeapp.data.network.response.ConvertCurrency
import com.example.exchangeapp.domain.ApiRepository
import com.example.exchangeapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {
    suspend operator fun invoke(from: String,to: String,amount: Int) : Flow<ConvertCurrency> =
        apiRepository.convertCurrency( from = from, to = to, amount = amount)
}