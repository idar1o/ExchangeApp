package com.example.exchangeapp.data


import com.example.exchangeapp.data.network.ApiService
import com.example.exchangeapp.data.network.response.ConvertCurrency
import com.example.exchangeapp.data.network.response.MultiCurrencyResponse
import com.example.exchangeapp.domain.ApiRepository
import com.example.exchangeapp.utils.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ApiRepository {
    override suspend fun convertCurrency(
        from: String,
        to: String,
        amount: Int
    ): Flow<ConvertCurrency> {
        return flow {
            val response =
                api.convertCurrency(
                    from = from,
                    to = to,
                    amount = amount
                ).execute()
            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            } else {
                throw Exception(response.errorBody()?.string())
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun multiConvertCurrency(
        from: String,
        to: String
    ): Flow<MultiCurrencyResponse> {
        return flow {
            val response = api.fetchMultiConversion(from = from, to = to)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}
