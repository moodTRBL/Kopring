package com.kopring.service

import com.kopring.domain.Board
import org.springframework.web.multipart.MultipartFile

interface FileUploadService {
    fun saveFile(multipartFile: List<MultipartFile>, board: Board)
}