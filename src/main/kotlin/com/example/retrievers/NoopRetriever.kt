package com.example.retrievers

import java.math.BigDecimal
import kotlin.math.absoluteValue

class NoopRetriever: PriceRetriever {
    override fun getPrice(): BigDecimal {
        return (420 + kotlin.random.Random.nextInt().absoluteValue % 420).toBigDecimal()
    }
}
