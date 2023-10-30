package com.example.retrievers

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.math.BigDecimal
import java.time.Duration

class YandexPriceRetriever(
    private var driver: WebDriver,
    private val url: String
) : PriceRetriever {

    private val priceRegex = Regex("([\\d.]+)₽")

    override fun getPrice(): BigDecimal {
        driver.get(url)

        WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(By.tagName("header")))

        return findPriceFirstAttempt()
            ?: run { findPriceSecondAttempt() }
            ?: run { findPriceThirdAttempt() }
            ?: run { throw RuntimeException(":(") }

    }

    private fun findPriceFirstAttempt(): BigDecimal? {
        return driver.findElements(By.tagName("h3"))
            .asSequence()
            .map { it.text }
            .filter { it.contains('₽') || it.contains("Цена с картой") }
            .map { it.filterNot { it2 -> it2 == ' ' || it2 == ' ' } }
            .mapNotNull { priceRegex.find(it)?.groups?.get(1)?.value }
            .map { it.toBigDecimal() }
            .firstOrNull()
    }

    private fun findPriceSecondAttempt(): BigDecimal? {
        return driver.findElements(By.className("_1stjo"))
            .asSequence()
            .map { it.text }
            .filter { it.contains('₽') || it.contains("Цена с картой") }
            .map { it.filterNot { it2 -> it2 == ' ' || it2 == ' ' } }
            .mapNotNull { priceRegex.find(it)?.groups?.get(1)?.value }
            .map { it.toBigDecimal() }
            .firstOrNull()
    }

    private fun findPriceThirdAttempt(): BigDecimal? {
        return driver.findElements(By.tagName("span"))
            .asSequence()
            .filter { it.text == "₽" }
            .map { it.findElement(By.xpath("./..")) }
            .map { it.text }
            .filter { it.contains('₽') || it.contains("Цена с картой") }
            .map { it.filterNot { it2 -> it2 == ' ' || it2 == ' ' } }
            .mapNotNull { priceRegex.find(it)?.groups?.get(1)?.value }
            .map { it.toBigDecimal() }
            .firstOrNull()
    }
}
