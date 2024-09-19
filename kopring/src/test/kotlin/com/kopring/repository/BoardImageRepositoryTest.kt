package com.kopring.repository

import com.kopring.domain.Board
import com.kopring.domain.BoardImage
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardImageRepositoryTest @Autowired constructor(
    private val boardImageRepository: BoardImageRepository,
    private val boardRepository: BoardRepository,
    private val entityManager: EntityManager
) : BehaviorSpec({
    extensions(SpringExtension)

    beforeEach {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0")
        entityManager.createNativeQuery("TRUNCATE TABLE board_image")
        entityManager.createNativeQuery("TRUNCATE TABLE board")
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1")
    }

    Given("BoardImage와 Board가 주어진다") {
        var board: Board = Board("title", "content")
        for (i in 1..3) {
            val boardImage: BoardImage = BoardImage("origin$i", "save$i", board)
            When("Board를 $i 번째 BoardImage와 매핑하여 저장한다") {
                boardRepository.save(board)
                boardImageRepository.save(boardImage)
                Then("저장이 성공한다") {
                    val resultBoardImage = boardImageRepository.findById(boardImage.id!!).get()
                    val resultBoard = boardRepository.findById(board.id!!).get()
                    resultBoardImage.id shouldBe boardImage.id
                    resultBoardImage.originFileName shouldBe boardImage.originFileName
                    resultBoardImage.saveFileName shouldBe boardImage.saveFileName
                    resultBoard.boardImages.find { it.id == boardImage.id } shouldNotBe null
                }
            }
        }
    }
})