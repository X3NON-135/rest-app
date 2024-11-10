package org.restapp.repository

import org.restapp.model.MovieCharacter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieCharacterRepository : JpaRepository<MovieCharacter?, Long?> {
    fun findAllByExternalIdIn(ids: Set<Long?>?): List<MovieCharacter?>?
    fun findAllByNameContains(namePart: String?): List<MovieCharacter?>?
}