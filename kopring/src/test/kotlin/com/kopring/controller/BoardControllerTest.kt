package com.kopring.controller

import com.google.gson.Gson
import com.kopring.dto.request.BoardWriteRequest
import com.kopring.dto.response.BoardWriteResponse
import com.kopring.service.BoardService
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
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.time.LocalDateTime

//@SpringBootTest
@WebMvcTest(controllers = [BoardController::class])
class BoardControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    @MockBean
    val boardService: BoardService
) : BehaviorSpec({

    afterSpec {
        clearAllMocks()
    }

    Given("boardController가 존재한다.") {
        every { boardService.writeBoard(any()) } returns BoardWriteResponse("title", HttpStatus.OK, LocalDateTime.now())
        When("{/api/board/write}로 POST요청을 보낸다") {
            Then("boardService가 saveBoard()를 호출한다") {
                verify(exactly = 1) { boardService.writeBoard(any()) }
            }

            val boardWriteRequest = BoardWriteRequest("title", "content", listOf())
            val result = mockMvc.post("/api/board/write") {
                content = boardWriteRequest
            }
            Then("response가 반환된다") {
                val response = Gson().fromJson(result.andReturn().response.contentAsString, BoardWriteResponse::class.java)
                response.title shouldBe boardWriteRequest.title
                response.httpStatus shouldBe HttpStatus.OK
            }
        }
    }
    TODO("BoardEditRequest 유효성 검사 테스트")
    TODO("BoardWriteRequest 파일 업로드 테스트")
})