package org.restapp.dto.external

import org.restapp.model.Gender
import org.restapp.model.Status

data class CharacterResponseDto(
    val id: Long? = null,
    val externalId: Long? = null,
    val name: String? = null,
    val status: Status? = null,
    val gender: Gender? = null,
) {
    fun setId(newId: Long?): CharacterResponseDto = this.copy(id = newId)
    fun setExternalId(newExternalId: Long?): CharacterResponseDto = this.copy(externalId = newExternalId)
    fun setName(newName: String?): CharacterResponseDto = this.copy(name = newName)
    fun setStatus(newStatus: Status?): CharacterResponseDto = this.copy(status = newStatus)
    fun setGender(newGender: Gender?): CharacterResponseDto = this.copy(gender = newGender)
}