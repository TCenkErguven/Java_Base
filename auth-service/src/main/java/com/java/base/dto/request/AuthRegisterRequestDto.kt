package com.java.base.dto.request

data class AuthRegisterRequestDto (
    val name: String,
    val surname: String,
    val birthDate: Long,
    val password: String,
    val rePassword: String,
)