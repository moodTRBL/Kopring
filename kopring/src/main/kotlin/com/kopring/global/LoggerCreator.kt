package com.kopring.global

import org.slf4j.LoggerFactory

abstract class LoggerCreator {
    val log = LoggerFactory.getLogger(javaClass)
}