package com.kopring.dto.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.kopring.domain.Board
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import org.springframework.http.HttpStatus
import org.springframework.web.multipart.MultipartFile

@JsonIgnoreProperties()
data class BoardWriteRequest(
    val title: String,
    val content: String,
)