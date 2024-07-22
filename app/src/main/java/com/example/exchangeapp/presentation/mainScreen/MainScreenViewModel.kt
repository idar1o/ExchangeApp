package com.example.exchangeapp.presentation.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeapp.data.network.response.ConvertCurrency
import com.example.exchangeapp.data.network.response.ResultResponse
import com.example.exchangeapp.domain.usecases.ConvertCurrencyUseCase
import com.example.exchangeapp.domain.usecases.MultiConvertCurrenciesUseCase
import com.example.exchangeapp.presentation.model.CurrencyPresentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val multiConvertCurrenciesUseCase: MultiConvertCurrenciesUseCase
): ViewModel() {
    private val _convertedSumm = MutableStateFlow(CurrencyPresentState() )
    val convertedSumm : StateFlow<CurrencyPresentState> = _convertedSumm



    fun convertCurrency(from : String, to : String, amount : Int){
        viewModelScope.launch {
            convertCurrencyUseCase.invoke(
                from =from,
                to = to,
                amount = amount
            ).catch { e ->
                _convertedSumm.value.loadError = e.message.toString()

                Log.d("LOL", "Error:${e.message}")
            }
            .collect { data ->
                _convertedSumm.value = _convertedSumm.value.copy(
                    currencies = "Amount $amount $from: $to = ${amount * data.result.rate}"
                )
            }
        }
    }

    fun multiConvertCurrency(from: String, to: String, amount: Int){
        viewModelScope.launch {
            multiConvertCurrenciesUseCase.invoke(from, to)
                .catch { e ->
                    _convertedSumm.value = _convertedSumm.value.copy(
                        loadError = e.message.toString()
                    )
                    Log.d("LOL", "Error: ${e.message}")
                }
                .collect { data ->
                    var res = ""
                    data.result?.forEach { (str, double) ->
                        res += "$str $double, "
                    }
                    Log.d("LOL", res)
                    _convertedSumm.value = _convertedSumm.value.copy(
                        currencies = "Amount $amount $from: $res"
                    )
                }
        }
    }
}