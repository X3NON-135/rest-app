package org.restapp.model

import javax.persistence.*

@Entity
@Table(name = "movie_characters")
data class MovieCharacter(
    @Id
    @GeneratedValue(generator = "movie_characters_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "movie_characters_id_seq", sequenceName = "movie_characters_id_seq", allocationSize = 1)
    val id: Long? = null,
    val externalId: Long? = null,
    val name: String? = null,

    @Enumerated(EnumType.STRING)
    val status: Status? = null,

    @Enumerated(EnumType.STRING)
    val gender: Gender? = null,
) {
    fun setId(newId: Long?): MovieCharacter = this.copy(id = newId)
    fun setExternalId(newExternalId: Long?): MovieCharacter = this.copy(externalId = newExternalId)
    fun setName(newName: String?): MovieCharacter = this.copy(name = newName)
    fun setStatus(newStatus: Status?): MovieCharacter = this.copy(status = newStatus)
    fun setGender(newGender: Gender?): MovieCharacter = this.copy(gender = newGender)
}