package com.example.config.props

import com.example.retrievers.PriceRetriever
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime

@Component
@ConfigurationProperties(prefix = "app")
data class RetrieverProperties(
    var retrievers: List<RetrieverProperty>
)

@ConstructorBinding
data class RetrieverProperty(
    var retriever: Retrievers,
    var url: String,
    var name: String,
) {
    enum class Retrievers {
        YANDEX,
        OZON,
        NOOP
    }
}


