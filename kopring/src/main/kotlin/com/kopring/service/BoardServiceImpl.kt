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
class BoardServiceImpl @Autowired constructor(
    private val boardRepository: BoardRepository,
    private val fileUploadService: FileUploadService
) : BoardService {

    override fun writeBoard(
        request: BoardWriteRequest
    ): BoardWriteResponse {
        val board: Board = Board.of(request)
        val result: Board = boardRepository.save(board)
        fileUploadService.saveFile(request.files, board)
        return BoardWriteResponse.from(result)
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