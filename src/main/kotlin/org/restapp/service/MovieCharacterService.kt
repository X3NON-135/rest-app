package org.restapp.service

import org.restapp.model.MovieCharacter

interface MovieCharacterService {
    val randomCharacter: MovieCharacter
    fun findAllByNameContains(namePart: String?): List<MovieCharacter?>
}