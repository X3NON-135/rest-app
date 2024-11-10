package org.restapp.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@Component
class HttpClient(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper
) {
    operator fun <T> get(url: String, clazz: Class<T>?): T {
        try {
            val response = restTemplate.getForObject(url, String::class.java)
            return objectMapper.readValue(response, clazz)
        } catch (e: RestClientException) {
            throw RuntimeException("Can't fetch info from URL $url", e)
        }
    }
}