package org.restapp.controller

import io.swagger.annotations.ApiOperation
import org.restapp.dto.external.CharacterResponseDto
import org.restapp.dto.mapper.MovieCharacterMapper
import org.restapp.model.MovieCharacter
import org.restapp.service.MovieCharacterService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/movie-characters")
class MovieCharacterController(
    characterService: MovieCharacterService,
    mapper: MovieCharacterMapper
) {
    private val characterService: MovieCharacterService
    private val mapper: MovieCharacterMapper

    init {
        this.characterService = characterService
        this.mapper = mapper
    }

    @get:ApiOperation(value = "get random character")
    @get:GetMapping("/random")
    val random: Any
        get() {
            val randomCharacter: MovieCharacter = characterService.randomCharacter
            return mapper.toResponseDto(randomCharacter)
        }

    @GetMapping("/by-name")
    @ApiOperation(value = "get any character by name or it's part")
    fun findAllByName(@RequestParam("name") namePart: String?): List<CharacterResponseDto> {
        return characterService.findAllByNameContains(namePart)
            .mapNotNull { mapper.toResponseDto(it ?: return@mapNotNull null) }
    }
}