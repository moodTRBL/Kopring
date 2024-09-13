package com.kopring.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

//수정은 null은 허용하지만(수정을 안하겠다는 뜻) 빈 값은 허용하지 않는다.
//모두 null인 경우도 허용하지 않는다.
data class BoardEditRequest(
    val boardId: Long,

    @field:NotBlank(message = "공백은 포함될 수 없습니다.")
    val title: String,

    @field:NotBlank(message = "공백은 포함될 수 없습니다.")
    val content: String
)