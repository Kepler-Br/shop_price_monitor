package com.example.retrievers

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.math.BigDecimal
import java.time.Duration

class OzonPriceRetriever(
    private val driver: WebDriver,
    private val url: String
) : PriceRetriever {
    private val priceRegex = Regex("([\\d.]+)₽")

    override fun getPrice(): BigDecimal {
        driver.get(url)

        WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(By.id("stickyHeader")))

        return firstAttempt()
            ?: run { secondAttempt() }
            ?: run { thirdAttempt() }
            ?: run { throw RuntimeException(":(") }
    }

    private fun firstAttempt(): BigDecimal? {
        return driver.findElements(By.className("l0l"))
            .asSequence()
            .map { it.text }
            .filter { it.contains('₽') }
            .map { it.filterNot { it2 -> it2 == ' ' || it2 == ' ' } }
            .mapNotNull { priceRegex.find(it)?.groups?.get(1)?.value }
            .map { it.toBigDecimal() }
            .firstOrNull()
    }

    private fun secondAttempt(): BigDecimal? {
        return driver.findElements(By.className("z8k"))
            .asSequence()
            .map { it.text }
            .filter { it.contains('₽') }
            .map { it.filterNot { it2 -> it2 == ' ' || it2 == ' ' } }
            .mapNotNull { priceRegex.find(it)?.groups?.get(1)?.value }
            .map { it.toBigDecimal() }
            .firstOrNull()
    }

    private fun thirdAttempt(): BigDecimal? {
        return driver.findElements(By.tagName("span"))
            .asSequence()
            .map { it.text }
            .filter { it.contains("Ozon") && it.contains('₽') }
            .map { it.filterNot { it2 -> it2 == ' ' || it2 == ' ' } }
            .mapNotNull { priceRegex.find(it)?.groups?.get(1)?.value }
            .map { it.toBigDecimal() }
            .firstOrNull()
    }
}
