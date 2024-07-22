package com.example.exchangeapp.presentation.mainScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainScreen(viewModel: MainScreenViewModel = hiltViewModel()) {
    val result by viewModel.convertedSumm.collectAsState()
    val error by viewModel.errorMessage.collectAsState()

    var amount by remember { mutableStateOf("0") }
    var fromCurrency by remember { mutableStateOf("USD") }
    val toCurrencies = remember { mutableStateListOf("EUR") }

    val currencies = listOf("USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AmountInputField(amount) {
            amount = it
        }

        Spacer(modifier = Modifier.height(16.dp))

        CurrencyDropdown(
            currencies = currencies,
            selectedCurrency = fromCurrency,
            onCurrencySelected = { selectedCurrency ->
                fromCurrency = selectedCurrency
            },
            modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        toCurrencies.forEachIndexed { index, currency ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                CurrencyDropdown(
                    currencies = currencies,
                    selectedCurrency = currency,
                    onCurrencySelected = { selectedCurrency ->
                        toCurrencies[index] = selectedCurrency
                    },
                    modifier = Modifier.weight(1f)
                )
                if (toCurrencies.size > 1) {
                    IconButton(
                        onClick = {
                            toCurrencies.removeAt(index)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove Currency"
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (toCurrencies.size < 6) {
            IconButton(
                onClick = {
                    toCurrencies.add(currencies.first { it != fromCurrency && it !in toCurrencies })
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Currency")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))



        if (amount.isDigitsOnly()) {

            val res = try {
                result.result.rate * amount.toInt()
            }catch (e:NumberFormatException){
                Log.d("LOL", e.message.toString())
                0.0
            }
            Text(text = "${res}")
        }
        Button(
            onClick = {

                if (toCurrencies.size == 1 && amount.isDigitsOnly()){
                    viewModel.convertCurrency(
                        from = fromCurrency,
                        to = toCurrencies.get(0),
                        amount = amount.toInt()
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Convert")
        }
    }
}

@Composable
fun AmountInputField(
    amount: String,
    onAmountChange: (String) -> Unit
) {
    OutlinedTextField(
        value = amount,
        onValueChange = {
            if (it.all { char -> char.isDigit() }) {
                onAmountChange(it)
            }
        },
        label = { Text("Amount") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CurrencyDropdown(
    currencies: List<String>,
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .wrapContentSize()
    ) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = selectedCurrency)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            currencies.forEach { currency ->
                DropdownMenuItem(onClick = {
                    onCurrencySelected(currency)
                    expanded = false
                },
                    text = {
                        Text(text = currency)
                    })
            }
        }
    }
}