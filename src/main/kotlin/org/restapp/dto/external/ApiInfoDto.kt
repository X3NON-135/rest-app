package org.restapp.dto.external

data class ApiInfoDto(
    val count: Int = 0,
    val pages: Int = 0,
    val next: String? = null,
    val prev: String? = null,
)