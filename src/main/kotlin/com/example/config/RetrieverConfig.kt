package com.example.config

import com.example.config.props.RetrieverProperties
import com.example.retrievers.PriceRetriever
import com.example.service.PriceRetrieverService
import io.micrometer.core.instrument.MeterRegistry
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.Duration
import java.time.LocalDateTime


@Configuration
class RetrieverConfig {
    @Bean
    fun webDriver(): WebDriver {
        val options = ChromeOptions()
        options.addArguments("user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36")
//    options.addArguments("--headless=new")
        return ChromeDriver(options)
    }

    @Bean
    fun config(
        properties: RetrieverProperties,
        driver: WebDriver,
        registry: MeterRegistry
    ): PriceRetrieverService {
        return PriceRetrieverService(
            properties,
            driver,
            Duration.ofDays(1),
            registry
        )
    }
}
