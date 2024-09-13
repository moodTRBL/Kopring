package com.kopring.repository

import com.kopring.domain.Board
import com.kopring.domain.Comment
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest @Autowired constructor(
    private val commentRepository: CommentRepository,
    private val boardRepository: BoardRepository,
    private val entityManager: EntityManager,
) : BehaviorSpec({
    extensions(SpringExtension)

    beforeTest {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0")
        entityManager.createNativeQuery("TRUNCATE TABLE board")
        entityManager.createNativeQuery("TRUNCATE TABLE comment")
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1")
    }

    Given("Comment와 Board가 주어진다") {
        var boards: MutableList<Board> = mutableListOf()
        for (i in 1..2) {
            val board = Board("title$i", "content$i")
            boards.add(board)
            boardRepository.save(board)
        }
        for (j in 1..5) {
            val boardIdx: Int = j/3;
            val comment = Comment("comment$j", boards[boardIdx])
            When("$boardIdx 번째 Board를 $j 번째 Comment와 매핑하여 저장한다") {
                commentRepository.save(comment)
                Then("저장이 성공한다") {
                    val resultComment: Comment = commentRepository.findById(comment.id!!).get();
                    val resultBoard: Board = boardRepository.findById(boards[boardIdx].id!!).get()
                    resultComment.id shouldBe comment.id
                    resultComment.content shouldBe comment.content
                    resultComment.board.id shouldBe resultBoard.id
                    resultBoard.comments.find { it.id == resultComment.id } shouldNotBe null
                }

                Then("$boardIdx 번째 Board에서 $j 번째 Comment를 삭제한다") {
                    val prevent: Int = commentRepository.findAll().size
                    val resultComment: Comment = commentRepository.findById(comment.id!!).get();
                    val resultBoard: Board = boardRepository.findById(boards[boardIdx].id!!).get()
                    resultBoard.comments.remove(resultComment)
                    resultBoard.comments.find { it.id == resultComment.id } shouldBe null
                    prevent - 1 shouldBe commentRepository.findAll().size                                               //해당 테스트를 굳이 넣은 이유는 내부쿼리저장소의 쿼리를 플러시하여 cascade 속성이 영속성 컨텍스트까지 적용되도록 하는 것이다
                    shouldThrow<NoSuchElementException> { commentRepository.findById(resultComment.id!!).get() }
                }

                Then("$boardIdx 번째 Board를 삭제하여 연관된 Comment도 삭제된다") {
                    val resultComment: Comment = commentRepository.findById(comment.id!!).get();
                    val resultBoard: Board = boardRepository.findById(boards[boardIdx].id!!).get()
                    boardRepository.delete(resultBoard)
                    entityManager.flush()
                    shouldThrow<NoSuchElementException> { commentRepository.findById(resultComment.id!!).get() }
                }
            }
        }

    }
})