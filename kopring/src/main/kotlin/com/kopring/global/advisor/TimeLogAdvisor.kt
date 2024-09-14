package com.kopring.global.advisor

import com.kopring.global.LoggerCreator
import lombok.extern.log4j.Log4j
import lombok.extern.slf4j.Slf4j
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.hibernate.validator.internal.util.logging.Log
import org.springframework.stereotype.Component

@Slf4j
@Aspect
@Component
class TimeLogAdvisor {

    companion object : LoggerCreator()

    @Pointcut("execution(* methodName(..))")
    fun pointCut() {}

    @Around("pointCut()")
    fun advice(joinPoint: ProceedingJoinPoint) {
        val startTime = System.currentTimeMillis()
        joinPoint.proceed()
        val endTime = System.currentTimeMillis()
        log.info("execute time: ${endTime - startTime} ms")
    }
}