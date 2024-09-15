package com.kopring.domain

import com.kopring.dto.request.BoardWriteRequest
import jakarta.persistence.*

@Entity
class BoardImage(
    val originFileName: String?,

    val saveFileName: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val board: Board
) : BaseEntity() {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    init {
        board.boardImages.add(this)
    }
}