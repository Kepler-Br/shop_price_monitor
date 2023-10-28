package com.example

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class YandexPriceRetriever(private var webDriver: WebDriver) : PriceRetriever {
    override fun getPrice(address: String): Int {
        webDriver.get(address)
        val source = webDriver.pageSource
        webDriver.findElements(By.tagName("h3")).map { it.text }.forEach { println("\"$it\"") }
        return getPriceFirst() ?: run {
            0
        }
    }

    private fun getPriceFirst(): Int? {
        webDriver.findElements(By.className("_1oI3I")).map { it.text }.forEach { println(it) }
        return 0
    }
}
