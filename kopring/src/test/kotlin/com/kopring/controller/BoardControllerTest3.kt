package com.kopring.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.kopring.dto.request.BoardWriteRequest
import com.kopring.dto.response.BoardWriteResponse
import com.kopring.service.BoardService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.impl.recording.WasNotCalled.method
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.multipart
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest
@MockBean(JpaMetamodelMappingContext::class)
class BoardControllerTest3(
    @Autowired
    private val mockMvc: MockMvc,

    @Autowired
    private val objectMapper: ObjectMapper,

    @MockkBean
    private val boardService: BoardService
) : DescribeSpec({
    describe("Board Controller가 존재한다") {
        val boardWriteResponse = BoardWriteResponse("title", HttpStatus.OK, LocalDateTime.now())
        val imageFiles = listOf(
            MockMultipartFile("files", "test1.txt", "text/plain", "test1".toByteArray()),
            MockMultipartFile("files", "test2.txt", "text/plain", "test2".toByteArray()),
        )
        every { boardService.writeBoard(any(), any()) } returns boardWriteResponse
        val boardWriteRequest = BoardWriteRequest("title", "content")
        val content = objectMapper.writeValueAsString(boardWriteRequest)
        val request = MockMultipartFile("request", "request.json", "application/json", content.toByteArray(StandardCharsets.UTF_8))
        mockMvc.multipart(HttpMethod.POST, "/api/board/write") {
            file(request)
            file(imageFiles[0])
            file(imageFiles[1])
        }.andExpect { status { isOk() } }
        verify(exactly = 1) { boardService.writeBoard(any(),any()) }
    }
})