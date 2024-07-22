package com.example.exchangeapp.data.network.response

import com.google.gson.annotations.SerializedName

data class MultiCurrencyResponse(
    @SerializedName("base")
    val base: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("results")
    val result: Map<String, Double>,
    @SerializedName("updated")
    val updated: String,
    @SerializedName("ms")
    val ms: Int
)
