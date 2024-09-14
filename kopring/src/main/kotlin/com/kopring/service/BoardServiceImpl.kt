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
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@Transactional
@Service
abstract class BoardServiceImpl @Autowired constructor(
    val boardRepository: BoardRepository,
    val fileUploadService: FileUploadService
) : BoardService {

    override fun writeBoard(
        request: BoardWriteRequest,
        file: MultipartFile
    ): BoardWriteResponse {
        val board: Board = Board.of(request)
        fileUploadService.saveFile(file, board)
        return BoardWriteResponse.from(boardRepository.save(board))
    }

    override fun editBoard(
        request: BoardEditRequest
    ): BoardEditResponse {
        var board: Board = boardRepository.findById(request.boardId).orElseThrow();
        board.title = request.title
        board.content = request.content
        return BoardEditResponse.from(board)
    }
}