package com.example

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions


fun main(args: Array<String>) {
    val options = ChromeOptions()
    options.addArguments("user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36")
    options.addArguments("--headless=new")
    val driver: WebDriver = ChromeDriver(options)
    YandexPriceRetriever(driver).getPrice("http://localhost:8080/p")
    driver.close()
}
