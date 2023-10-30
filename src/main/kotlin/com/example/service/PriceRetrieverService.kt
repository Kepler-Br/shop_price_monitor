package com.example.service

import com.example.config.RetrieverEntry
import com.example.config.RetrieverProperties
import com.example.config.RetrieverProperty
import com.example.config.props.RetrieverProperties
import com.example.config.props.RetrieverProperty
import com.example.retrievers.NoopRetriever
import com.example.retrievers.OzonPriceRetriever
import com.example.retrievers.PriceRetriever
import com.example.retrievers.YandexPriceRetriever
import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import org.openqa.selenium.WebDriver
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.temporal.TemporalAmount

class RetrieverEntry(
    val priceRetriever: PriceRetriever,
) {
    var lastRunAt: LocalDateTime = LocalDateTime.MIN
    var lastPrice: BigDecimal? = null

    fun retrieve() {
        this.lastPrice = priceRetriever.getPrice()
    }
}

class PriceRetrieverService(
    properties: RetrieverProperties,
    driver: WebDriver,
    val period: TemporalAmount,
    meterRegistry: MeterRegistry
) {

    val retrieverList: MutableList<RetrieverEntry> = mutableListOf()

    init {
        for (property in properties.retrievers) {
            val retriever = when (property.retriever) {
                RetrieverProperty.Retrievers.YANDEX -> YandexPriceRetriever(driver, property.url)
                RetrieverProperty.Retrievers.OZON -> OzonPriceRetriever(driver, property.url)
                RetrieverProperty.Retrievers.NOOP -> NoopRetriever()
            }

            val entry = RetrieverEntry(retriever)
            Gauge.builder("price", entry) {
                it.lastPrice?.toDouble() ?: Double.NaN
            }
                .tag("url", property.url)
                .tag("name", property.name)
                .tag("source", property.retriever.name)
                .register(meterRegistry)

            retrieverList.add(entry)
        }
    }

    fun run() {
        retrieverList.find {
            it.lastRunAt + period < LocalDateTime.now()
        }?.let {
            it.retrieve()
        }
    }
}
