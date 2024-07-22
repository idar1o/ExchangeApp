package com.example.exchangeapp.data.network.response

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class ResultDeserializer: JsonDeserializer<ResultResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ResultResponse {
        val jsonObject = json?.asJsonObject ?: throw JsonParseException("Invalid JSON")

        val rate = jsonObject["rate"].asDouble

        val dynamicRates = mutableMapOf<String, Double>()
        jsonObject.entrySet().forEach { entry ->
            if (entry.key != "rate") {
                dynamicRates[entry.key] = entry.value.asDouble
            }
        }

        return ResultResponse(rate, dynamicRates)
    }
}