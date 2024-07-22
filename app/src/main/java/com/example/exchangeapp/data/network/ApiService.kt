package com.example.exchangeapp.data.network

import com.example.exchangeapp.data.network.response.ConvertCurrency
import com.example.exchangeapp.utils.Constants.API_KEY
import com.example.exchangeapp.utils.Resource
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("convert")
    fun convertCurrency(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Int,
    ): Call<ConvertCurrency>
}