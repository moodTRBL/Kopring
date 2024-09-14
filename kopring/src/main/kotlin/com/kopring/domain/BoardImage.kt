package com.kopring.domain

import jakarta.persistence.*

@Entity
class BoardImage(
    val originFileName: String?,

    val saveFileName: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val board: Board
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}