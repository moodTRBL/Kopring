package com.kopring.domain

import com.kopring.dto.request.BoardWriteRequest
import jakarta.persistence.*
import org.springframework.data.domain.Auditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
class Board(
    var title: String,
    var content: String
) : BaseEntity() {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    var viewCount: Long = 0

    @OneToMany(mappedBy = "board", cascade = [(CascadeType.ALL)], orphanRemoval = true, fetch = FetchType.LAZY)
    var comments: MutableList<Comment> = mutableListOf()

    companion object {
        fun of(request: BoardWriteRequest): Board {
            return Board(
                title = request.title,
                content = request.content
            )
        }
    }
}