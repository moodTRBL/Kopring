package com.kopring.controller

import com.kopring.service.BoardService
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*

class BoardControllerTest : BehaviorSpec({
    val boardService: BoardService = mockk<BoardService>()
    val boardController = BoardController(boardService)

    afterSpec {
        clearAllMocks()
    }

    TODO("BoardEditRequest 유효성 검사 테스트")
    TODO("BoardWriteRequest 파일 업로드 테스트")
})