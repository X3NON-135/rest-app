package org.restapp.dto.mapper

import org.restapp.dto.external.ApiCharacterDto
import org.restapp.dto.external.CharacterResponseDto
import org.restapp.model.Gender
import org.restapp.model.MovieCharacter
import org.restapp.model.Status
import org.springframework.stereotype.Component

@Component
class MovieCharacterMapper {
    fun parseApiCharacterResponseDto(dto: ApiCharacterDto): MovieCharacter {
        val movieCharacter = MovieCharacter()
        movieCharacter.setName(dto.name)
        movieCharacter.setGender(Gender.valueOf(dto.gender?.uppercase() ?: Gender.UNKNOWN.name))
        movieCharacter.setStatus(Status.valueOf(dto.status?.uppercase() ?: Status.UNKNOWN.name))
        movieCharacter.setExternalId(dto.id)
        return movieCharacter
    }

    fun toResponseDto(movieCharacter: MovieCharacter): CharacterResponseDto {
        val responseDto = CharacterResponseDto()
        responseDto.setId(movieCharacter.id)
        responseDto.setExternalId(movieCharacter.externalId)
        responseDto.setName(movieCharacter.name)
        responseDto.setStatus(movieCharacter.status)
        responseDto.setGender(movieCharacter.gender)
        return responseDto
    }
}