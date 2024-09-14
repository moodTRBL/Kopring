package com.kopring.repository

import com.kopring.domain.BoardImage
import org.springframework.data.jpa.repository.JpaRepository

interface BoardImageRepository : JpaRepository<BoardImage, Long> {
}