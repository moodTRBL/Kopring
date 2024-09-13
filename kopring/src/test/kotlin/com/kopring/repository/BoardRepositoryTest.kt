package com.kopring.repository

import com.kopring.domain.Board
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.date.shouldBeAfter
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.lang.Thread.sleep
import java.util.NoSuchElementException

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardRepositoryTest @Autowired constructor(
    private val boardRepository: BoardRepository,
    private val entityManager: EntityManager
) : BehaviorSpec({
    extensions(SpringExtension)

    beforeEach {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0")
        entityManager.createNativeQuery("TRUNCATE TABLE board")
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1")
    }

    Given("Board가 주어진다") {
        for (i in 1..5) {
            var board = Board("title$i", "content$i")
            When("$i 번째 Board를 저장한다") {
                board = boardRepository.save(board)
                Then("저장이 성공한다") {
                    val result: Board = boardRepository.findById(board.id!!).get()
                    result.id shouldBe board.id
                    result.title shouldBe board.title
                    result.content shouldBe board.content
                    result.comments.size shouldBe 0
                    result.createTime shouldNotBe null
                    result.updateTime shouldNotBe null
                }

                Then("없는 Board를 찾아 예외를 던진다") {
                    shouldThrow<NoSuchElementException> { boardRepository.findById((i+1)*1L).get() }
                }
            }

            When("$i 번째 Board를 변경한다") {
                board.title = "title"
                board.content = "content"
                boardRepository.save(board)
                Then("변경이 성공한다") {
                    val result: Board = boardRepository.findById(board.id!!).get()
                    result.title shouldBe "title"
                    result.content shouldBe "content"
                    result.updateTime?.shouldBeAfter(result.createTime!!)
                }
            }
        }
    }

})