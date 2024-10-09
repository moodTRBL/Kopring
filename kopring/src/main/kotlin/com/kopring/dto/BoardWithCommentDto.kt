package com.kopring.dto

data class BoardWithCommentDto(
    val title: String,
    val content: String,
    val comments: List<CommentDto>
)

data class CommentDto(
    val content: String,
)