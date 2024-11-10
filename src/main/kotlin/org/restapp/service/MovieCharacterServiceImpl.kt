package org.restapp.service

import org.restapp.dto.external.ApiCharacterDto
import org.restapp.dto.external.ApiResponseDto
import org.restapp.dto.mapper.MovieCharacterMapper
import org.restapp.model.MovieCharacter
import org.restapp.repository.MovieCharacterRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.annotation.PostConstruct

@Service
class MovieCharacterServiceImpl(
    private val httpClient: HttpClient,
    private val movieCharacterRepository: MovieCharacterRepository,
    private val mapper: MovieCharacterMapper
) : MovieCharacterService {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostConstruct
    @Scheduled(cron = "0 7 * * * ?")
    private fun syncExternalCharacters() {
        logger.info("syncExternalCharacters method was invoked at " + LocalDateTime.now())
        var apiResponseDto: ApiResponseDto? = httpClient.get(
            url = API_URL,
            clazz = ApiResponseDto::class.java
        )

        saveDtoToDB(apiResponseDto)
        val nextElement = apiResponseDto?.info?.next

        while (nextElement != null) {
            apiResponseDto = httpClient.get(
                url = nextElement,
                clazz = ApiResponseDto::class.java
            )
            saveDtoToDB(apiResponseDto)
        }
    }

    override val randomCharacter: MovieCharacter
        get() {
            val count: Long = movieCharacterRepository.count()
            val randomId = (Math.random() * count).toLong() + 1
            return movieCharacterRepository.findById(randomId).orElseThrow {
                RuntimeException("Can't get Movie Character by id $randomId")
            }!!
        }

    override fun findAllByNameContains(namePart: String?): List<MovieCharacter?> {
        return movieCharacterRepository.findAllByNameContains(namePart) ?: emptyList()
    }

    fun saveDtoToDB(responseDto: ApiResponseDto?): List<MovieCharacter> {
        val externalDtos: Map<Long, ApiCharacterDto> = responseDto?.results
            ?.filter { it.id != null }
            ?.associateBy { it.id!! }
            ?: emptyMap()

        val externalIds: MutableSet<Long> = externalDtos.keys.toMutableSet()
        val dbMovieCharacter: List<MovieCharacter?> = movieCharacterRepository.findAllByExternalIdIn(externalIds) ?: emptyList()
        val existingCharacters: List<MovieCharacter> = dbMovieCharacter.flatMap { listOfNotNull(it) }
        val existingCharactersWithIds: Map<Long, MovieCharacter> = existingCharacters
            .filter { it.externalId != null }
            .associateBy { it.externalId!! }

        val existingIds = existingCharactersWithIds.keys

        externalIds.removeAll(existingIds)

        val charactersToSave: List<MovieCharacter> = externalIds.mapNotNull {
            mapper.parseApiCharacterResponseDto(
                externalDtos[it] ?: return@mapNotNull null
            )
        }
        return movieCharacterRepository.saveAll(charactersToSave)
    }

    companion object {
        private const val API_URL = "https://rickandmortyapi.com/api/character"
    }
}