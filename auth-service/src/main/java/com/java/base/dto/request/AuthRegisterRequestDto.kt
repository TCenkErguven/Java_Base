package com.java.base.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AuthRegisterRequestDto (
    @field:NotBlank(message = "Name must not be blank")
    val name: String,
    @field:NotBlank(message = "surname must not be blank")
    val surname: String,
    @field:NotNull(message = "Birthday must not be empty")
    val birthDate: Long,
    @field:NotBlank(message = "Password must not be empty")
    val password: String,
    @field:NotBlank(message = "RePassword must not be empty")
    val rePassword: String,
)