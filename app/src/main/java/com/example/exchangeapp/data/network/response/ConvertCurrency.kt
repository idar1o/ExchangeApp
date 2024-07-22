package com.example.exchangeapp.data.network.response

import com.google.gson.annotations.SerializedName

data class ConvertCurrency(
    @SerializedName("base")
    val base: String,
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("ms")
    val ms: Int,
    @SerializedName("result")
    val result: ResultResponse,
)
