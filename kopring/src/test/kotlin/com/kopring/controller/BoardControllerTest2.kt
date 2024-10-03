//package com.kopring.controller
//
//import com.kopring.dto.response.BoardWriteResponse
//import com.kopring.repository.BoardRepository
//import com.kopring.service.BoardService
//import com.kopring.service.BoardServiceImpl
//import com.kopring.service.FileUploadService
//import com.ninjasquad.springmockk.MockkBean
//import io.kotest.core.spec.style.AnnotationSpec
//import io.mockk.*
//import io.mockk.junit5.MockKExtension
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.MockitoAnnotations
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.mock.mockito.MockBean
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
//import org.springframework.http.HttpStatus
//import org.springframework.mock.web.MockMultipartFile
//import org.springframework.test.context.junit.jupiter.SpringExtension
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.multipart
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers
//import java.nio.charset.StandardCharsets
//import java.time.LocalDateTime
//
////@SpringBootTest
////@AutoConfigureMockMvc
//@WebMvcTest(BoardController::class)
//@ExtendWith(SpringExtension::class)
////@MockBean(JpaMetamodelMappingContext::class)
//class BoardControllerTest2 : AnnotationSpec() {
//
//    @MockkBean
//    private lateinit var boardService: BoardService
//
//    @Autowired
//    private lateinit var mockMvc: MockMvc
//
//    @Test
//    fun `Board를 wrtie하는 POST요청을 보낸다`() {
//        val boardWriteResponse = BoardWriteResponse("title", HttpStatus.OK, LocalDateTime.now())
//        val imageFiles = listOf(
//            MockMultipartFile("file1", "test1.txt", "text/plain", "test1".byteInputStream(StandardCharsets.UTF_8)),
//            MockMultipartFile("file2", "test2.txt", "text/plain", "test2".byteInputStream(StandardCharsets.UTF_8)),
//        )
//        every { boardService.writeBoard(any()) } returns boardWriteResponse
//        mockMvc.multipart("/api/board/write") {
//            param("title", "title")
//            param("content", "content")
//            file("file1", imageFiles[0].bytes)
//            file("file2", imageFiles[1].bytes)
//        }.andExpectAll {
//            status { isOk() }
//            MockMvcResultMatchers.jsonPath("$.title").value(boardWriteResponse.title)
//        }
//        verify(exactly = 1) { boardService.writeBoard(any()) }
//    }
//}