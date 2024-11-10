package org.restapp.dto.external

data class ApiResponseDto(
    val info: ApiInfoDto? = null,
    val results: List<ApiCharacterDto> = emptyList(),
)