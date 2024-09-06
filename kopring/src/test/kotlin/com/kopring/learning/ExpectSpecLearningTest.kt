package com.kopring.learning

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ExpectSpecLearningTest : ExpectSpec({
    context("sub") {
        expect("3-2=1") {
            val result: Int = 3 - 2
            result shouldBe 1
        }
    }
}) {

}