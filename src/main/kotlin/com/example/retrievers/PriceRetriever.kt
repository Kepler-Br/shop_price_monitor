package com.example.retrievers

import java.math.BigDecimal

interface PriceRetriever {

    fun getPrice(): BigDecimal
}
