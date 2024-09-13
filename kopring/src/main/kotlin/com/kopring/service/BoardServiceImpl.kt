package com.kopring.service

import com.kopring.domain.Board
import com.kopring.dto.request.BoardEditRequest
import com.kopring.dto.request.BoardWriteRequest
import com.kopring.dto.response.BoardEditResponse
import com.kopring.dto.response.BoardWriteResponse
import com.kopring.repository.BoardRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Transactional
@Service
class BoardServiceImpl @Autowired constructor(
    val boardRepository: BoardRepository
) : BoardService {

    override fun writeBoard(
        request: BoardWriteRequest
    ): BoardWriteResponse {
        val board: Board = Board.of(request)
        return BoardWriteResponse.from(boardRepository.save(board))
    }

    override fun editBoard(
        request: BoardEditRequest
    ): BoardEditResponse {
        var board: Board = boardRepository.findById(request.boardId).orElseThrow();
        if(request.title != null) board.title = request.title
        if(request.content != null) board.content = request.content
        return BoardEditResponse.from(board)
    }
}