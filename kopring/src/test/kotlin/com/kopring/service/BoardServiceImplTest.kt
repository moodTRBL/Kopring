package com.kopring.service

import com.kopring.domain.Board
import com.kopring.dto.request.BoardEditRequest
import com.kopring.dto.request.BoardWriteRequest
import com.kopring.dto.response.BoardEditResponse
import com.kopring.dto.response.BoardWriteResponse
import com.kopring.repository.BoardRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.exactly
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockMultipartFile
import java.nio.charset.StandardCharsets
import java.util.*

class BoardServiceImplTest : BehaviorSpec({
    val boardRepository: BoardRepository = mockk<BoardRepository>()
    val fileUploadService: FileUploadService = mockk<FileUploadService>()
    val boardService: BoardService = BoardServiceImpl(boardRepository,fileUploadService)

    //Given이 수행되고 목을 초기화한다
    afterSpec {
        clearAllMocks()
    }

    Given("Board엔티티를 write하는 service가 존재한다") {
        every { boardRepository.save(any()) } returns Board("title", "content")
        every { fileUploadService.saveFile(any(),any()) } returns Unit
        When("write를 호출한다") {
            val request = BoardWriteRequest(title = "title", content = "content", listOf())
            val result: BoardWriteResponse = boardService.writeBoard(request)
            Then("boardRepository가 save()를 호출한다") {
                verify(exactly = 1) { boardRepository.save(any()) }
            }
            Then("fileUploadService가 saveFile()을 호출한다") {
                verify(exactly = 1) { fileUploadService.saveFile(any(),any()) }
            }
            Then("response가 반환된다") {
                result.title shouldBe request.title
                result.httpStatus shouldBe HttpStatus.OK
            }
        }
    }

    Given("Board엔티티를 edit하는 service가 존재한다") {
        every { boardRepository.findById(any()) } returns Optional.of(Board("title", "content"))
        When("edit을 호출한다") {
            val request = BoardEditRequest(boardId = 1, title = "title1", content = "content1")
            val result: BoardEditResponse = boardService.editBoard(request)
            Then("boardRepository가 findById()를 호출한다") {
                verify(exactly = 1) { boardRepository.findById(any()) }
            }
            Then("변경이 성공한다") {
                result.title shouldBe request.title
                result.httpStatus shouldBe HttpStatus.OK
            }
        }
    }
})