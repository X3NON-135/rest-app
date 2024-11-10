package org.restapp.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.DefaultUriBuilderFactory
import java.net.HttpURLConnection

@Configuration
class RestTemplateConfig(
    private val restTemplateHeaders: RestTemplateHeaders,

    @Value("\${resttemplate.timeout.mills}")
    private val timeout: Int,
) {

    @Bean
    fun customRestTemplate(restTemplateBuilder: RestTemplateBuilder): RestTemplate {
        val requestFactory = object : SimpleClientHttpRequestFactory() {
            override fun prepareConnection(connection: HttpURLConnection, httpMethod: String) {
                super.prepareConnection(connection, httpMethod)
                connection.instanceFollowRedirects = false
            }
        }

        requestFactory.setConnectTimeout(timeout)
        requestFactory.setReadTimeout(timeout)

        val restTemplate = restTemplateBuilder.requestFactory { requestFactory }.build()

        restTemplate.interceptors.add(restTemplateHeaders)

        return restTemplate
    }
}