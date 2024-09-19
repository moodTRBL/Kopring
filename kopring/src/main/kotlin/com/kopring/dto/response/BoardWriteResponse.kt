package com.kopring.dto.response

import com.kopring.domain.Board
import jakarta.persistence.Entity
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class BoardWriteResponse(
    val title: String,
    val httpStatus: HttpStatus,
    val time: LocalDateTime
) {
    companion object {
        fun from(entity: Board): BoardWriteResponse =
            BoardWriteResponse(
                title = entity.title,
                httpStatus = HttpStatus.OK,
                time = LocalDateTime.now()
            )
    }
}