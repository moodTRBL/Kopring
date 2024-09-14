package com.kopring.service

import com.kopring.domain.Board
import com.kopring.domain.BoardImage
import com.kopring.global.ApplicationProperties
import com.kopring.repository.BoardImageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@Component
class DBFileUploadService @Autowired constructor(
    private val applicationProperties: ApplicationProperties,
    private val boardImageRepository: BoardImageRepository
) : FileUploadService{

    override fun saveFile(
        file: MultipartFile,
        board: Board
    ) {
        val originFileName: String? = file.originalFilename
        val saveFileName = createSaveFileName(originFileName)
        val filePath = getFilePath(saveFileName)
        file.transferTo(File(filePath))
        val boardImage: BoardImage = BoardImage(originFileName, saveFileName, board)
        boardImageRepository.save(boardImage)
    }

    private fun createSaveFileName(fileName: String?): String {
        val ext = extractExt(fileName)
        val fileId: String = UUID.randomUUID().toString()
        return "${fileId}.${ext}"
    }

    private fun extractExt(fileName: String?): String? {
        val idx = fileName?.lastIndexOf('.')
        return fileName?.substring(idx?.plus(1) as Int)
    }

    private fun getFilePath(fileName: String?): String {
        return "${applicationProperties.upload.path}${fileName}"
    }
}