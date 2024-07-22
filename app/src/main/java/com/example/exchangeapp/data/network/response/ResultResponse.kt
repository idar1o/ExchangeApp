package com.example.exchangeapp.data.network.response

import com.google.gson.annotations.SerializedName

data class ResultResponse(
    @SerializedName("rate") val rate: Double,
    val dynamicRates: Map<String, Double> // Для динамических валютных пар
)