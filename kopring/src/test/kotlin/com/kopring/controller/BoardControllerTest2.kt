package com.kopring.controller

import com.kopring.service.BoardService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.AnnotationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest2 : AnnotationSpec() {
    @MockkBean
    private lateinit var boardService: BoardService
    @Autowired
    private lateinit var mockMvc: MockMvc


}