package com.example

import com.example.retrievers.YandexPriceRetriever
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration


fun main(args: Array<String>) {
    val options = ChromeOptions()
    options.addArguments("user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36")
//    options.addArguments("--headless=new")
//    val driver: WebDriver = ChromeDriver(options)
//    try {
//        driver.get("https://market.yandex.ru/product--avtonomnyi-vr-shlem-oculus-quest-3-128-gb/102449726085")
//        var price = YandexPriceRetriever(driver).getPrice()
//        println(price)
//        WebDriverWait(driver, Duration.ofMinutes(10))
//            .until(ExpectedConditions.visibilityOf(driver.findElement(By.id("stickyHeader"))))
//    } catch (ex: RuntimeException) {
//        println("Oh, noes!")
//    }
//    driver.quit()
}
