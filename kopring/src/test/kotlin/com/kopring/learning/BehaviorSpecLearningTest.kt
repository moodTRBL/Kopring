package com.kopring.learning

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class BehaviorSpecLearningTest(
) : BehaviorSpec({
//    beforeTest {
//        println("before test")
//    }

    beforeEach {
        println("before each test")
    }

    Given("calculator") {
        val expression: Int = 1 + 2
        When("add") {
            val result = expression
            Then("3이 반환된다.") {
                result shouldBe 3
            }
        }
    }

    Given("calculator") {
        val expression: Int = 1 + 2
        When("add") {
            val result = expression
            Then("3이 반환된다.") {
                result shouldBe 3
            }
        }
    }

    Given("calculator") {
        val expression: Int = 1 + 2
        When("add") {
            val result = expression
            Then("3이 반환된다.") {
                result shouldBe 3
            }
        }
    }
})