package com.kopring.service

import com.kopring.domain.Board
import com.kopring.dto.request.BoardEditRequest
import com.kopring.dto.request.BoardWriteRequest
import com.kopring.dto.response.BoardEditResponse
import com.kopring.dto.response.BoardWriteResponse
import org.springframework.web.multipart.MultipartFile

interface BoardService {
    fun writeBoard(request: BoardWriteRequest, file: MultipartFile): BoardWriteResponse
    fun editBoard(request: BoardEditRequest): BoardEditResponse
}