package com.example.job

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RetrieverRunner {

    @Scheduled(cron = "0 * * * * *")
    fun run() {

    }
}
