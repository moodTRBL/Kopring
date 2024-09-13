package com.kopring.dto.response

import com.kopring.domain.Board
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class BoardEditResponse(
    val title: String,
    val httpStatus: HttpStatus,
    val time: LocalDateTime
) {
    companion object {
        fun from(entity: Board): BoardEditResponse {
            return BoardEditResponse(
                title = entity.title,
                httpStatus = HttpStatus.OK,
                time = LocalDateTime.now()
            )
        }
    }
}