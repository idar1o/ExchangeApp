package com.example.exchangeapp.presentation.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeapp.data.network.response.ConvertCurrency
import com.example.exchangeapp.data.network.response.ResultResponse
import com.example.exchangeapp.domain.usecases.ConvertCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase
): ViewModel() {
    private val _convertedSumm = MutableStateFlow(
        ConvertCurrency(
            base = "USD",
            amount =1,
            result = ResultResponse(
                rate = 0.0,
                dynamicRates = mapOf<String, Double>(Pair("EUR", 0.0))
            ),
            ms = 2
            ), )
    val convertedSumm : StateFlow<ConvertCurrency> = _convertedSumm

    private val _error = MutableStateFlow<String?>("")
    val errorMessage = _error

    init {

    }


    fun convertCurrency(from : String, to : String, amount : Int){
        viewModelScope.launch {
            convertCurrencyUseCase.invoke(
                from =from,
                to = to,
                amount = amount
            ).catch { e ->
                _error.value = e.message

                Log.d("LOL", "Error:${e.message}")
            }
            .collect { userDetails ->
                _convertedSumm.value = userDetails
            }
        }
    }
}