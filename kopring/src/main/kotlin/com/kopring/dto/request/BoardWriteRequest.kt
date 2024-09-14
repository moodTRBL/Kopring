package com.kopring.dto.request

import com.kopring.domain.Board
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import org.springframework.http.HttpStatus
import org.springframework.web.multipart.MultipartFile

data class BoardWriteRequest(
    val title: String,
    val content: String,
)