package com.arstagaev.testlazyrow3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arstagaev.testlazyrow3.ui.theme.TestlazyRow3Theme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val _currencyPrices = MutableStateFlow(listOf<Box>())
    val currencyPricesW: StateFlow<List<Box>> get() =  _currencyPrices //get() = _currencyPrices

    val initialCurrencyPrices = arrayListOf(
        Box(12,true),
        Box(122,true),
        Box(123,true),
        Box(124,true),
        Box(122,true)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        updR()


        setContent {
            TestlazyRow3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val currencyPrices = currencyPricesW.collectAsState()
                    LazyColumn {
                        itemsIndexed(currencyPrices.value) { index, currencyPrice ->

                            Rower(
                                currencyPrice = currencyPrice,
                                { onCardActive(index) },
                                index,
                            )

                        }
                    }
                }
            }
        }
    }

    private fun updR() {
        //currencyPrices =  provideCurrencyUpdateFlow()
        GlobalScope.launch {
            while (true) {
                delay(1)
                initialCurrencyPrices.forEachIndexed { index, box ->

                }
//                run loop@{
//                    _currencyPrices.value.forEachIndexed { index, currency ->
//                        //if (index == 5) return@loop
//                        initialCurrencyPrices += "WELLL${(0..100).random()}"
//                    }
//                }
                _currencyPrices.emit(initialCurrencyPrices)


                delay(100)
            }
        }
    }

    // onclicker
    private fun onCardActive(index: Int) {
        println("UPD")
        val UPDCur = Box(0,false)
        val mutableCurrencyPrices = _currencyPrices.value.toMutableList()
        mutableCurrencyPrices[index] = UPDCur
        _currencyPrices.value = mutableCurrencyPrices as MutableList<Box>
    }

    @Composable
    private fun Rower(currencyPrice: Box, onCardActive: () -> Unit, index: Int) {
        val rememberedTitle = remember { currencyPrice }

        Row(
            Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(if (currencyPrice.isReal) Color.Red else Color.Blue)
                .clickable {
                    onCardActive(index)
                    initialCurrencyPrices[index] = Box(7,!currencyPrice.isReal )
                    println(">>>> ${_currencyPrices.value.joinToString()}")
                }
        ) {
            Text(text = "${currencyPrice.toString()}",Modifier.fillMaxSize())
        }
    }


}

