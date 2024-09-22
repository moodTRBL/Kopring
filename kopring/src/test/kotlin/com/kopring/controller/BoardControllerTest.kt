package com.kopring.controller

import com.google.gson.Gson
import com.kopring.dto.request.BoardEditRequest
import com.kopring.dto.request.BoardWriteRequest
import com.kopring.dto.response.BoardEditResponse
import com.kopring.dto.response.BoardWriteResponse
import com.kopring.service.BoardService
import com.kopring.service.BoardServiceImpl
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.exactly
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.mock.web.MockPart
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.multipart
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime

//@SpringBootTest
@MockBean(JpaMetamodelMappingContext::class)
@WebMvcTest(controllers = [BoardController::class])
class BoardControllerTest(
    @Autowired
    val mockMvc: MockMvc,
    @MockkBean
    val boardService: BoardService
) : BehaviorSpec({

    afterSpec {
        clearAllMocks()
    }

    Given("boardController가 존재한다.") {
        every { boardService.writeBoard(any()) } returns BoardWriteResponse("title", HttpStatus.OK, LocalDateTime.now())
        When("{/api/board/write}로 POST요청을 보낸다") {
            val imageFiles = listOf(
                MockMultipartFile("file1", "test1.txt", "text/plain", "test1".byteInputStream(StandardCharsets.UTF_8)),
                MockMultipartFile("file2", "test2.txt", "text/plain", "test2".byteInputStream(StandardCharsets.UTF_8)),
            )
            val boardWriteRequest = BoardWriteRequest("title", "content", imageFiles)

            val result = mockMvc.multipart("/api/board/write") {
                param("title", boardWriteRequest.title)
                param("content", boardWriteRequest.content)
                file("image1", imageFiles[0].bytes)
                file("image2", imageFiles[1].bytes)
            }
            Then("boardService가 saveBoard()를 호출한다") {
                verify(exactly = 1) { boardService.writeBoard(any()) }
            }

            Then("response가 반환된다") {
                val response = Gson().fromJson(result.andReturn().response.contentAsString, BoardWriteResponse::class.java)
                response.title shouldBe boardWriteRequest.title
                response.httpStatus shouldBe HttpStatus.OK
            }
        }

        every { boardService.editBoard(any()) } returns BoardEditResponse("title", HttpStatus.OK, LocalDateTime.now())
        When("{/api/board/edit}로 PUT요청을 보낸다") {
            val boardEditRequest = BoardEditRequest(1, "title", "content")
            val result = mockMvc.put("/api/board/edit/1") {
                content = boardEditRequest
                contentType = MediaType.APPLICATION_JSON
            }
            Then("boardService가 saveBoard()를 호출한다") {
                verify(exactly = 1) { boardService.editBoard(any()) }
            }
            Then("response가 반환된다") {
                val response = Gson().fromJson(result.andReturn().response.contentAsString, BoardEditResponse::class.java)
                response.title shouldBe boardEditRequest.title
                response.httpStatus shouldBe HttpStatus.OK
            }
        }
    }
})