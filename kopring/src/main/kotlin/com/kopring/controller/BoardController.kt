package com.kopring.controller

import com.kopring.dto.request.BoardEditRequest
import com.kopring.dto.request.BoardWriteRequest
import com.kopring.dto.response.BoardEditResponse
import com.kopring.dto.response.BoardWriteResponse
import com.kopring.service.BoardService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/api/board")
@Controller
class BoardController @Autowired constructor(
    val boardService: BoardService
) {
    @PostMapping("/write")
    fun writeBoard(
        @RequestBody reqeuest: BoardWriteRequest
    ): ResponseEntity<BoardWriteResponse> {
        val response = boardService.writeBoard(reqeuest)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/edit/{boardId}")
    fun editBoard(
        @Valid @RequestBody reqeuest: BoardEditRequest
    ): ResponseEntity<BoardEditResponse> {
        val response = boardService.editBoard(reqeuest)
        return ResponseEntity.ok(response)
    }
}