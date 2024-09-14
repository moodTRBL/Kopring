package com.kopring.global

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "spring")
data class ApplicationProperties(
    val upload: Upload
) {
    data class Upload(
        val path: String
    )
}