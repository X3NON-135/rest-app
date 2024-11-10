package org.restapp.config

import org.springframework.http.HttpHeaders
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component

@Component
class RestTemplateHeaders(
) : ClientHttpRequestInterceptor {

    override fun intercept(
        request: org.springframework.http.HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        CUSTOM_HTTP_HEADERS.forEach {
            request.headers[it.key] = it.value
        }

        val response = execution.execute(request, body)
        return response
    }

    private companion object {
        val CUSTOM_HTTP_HEADERS = mapOf(
            HttpHeaders.USER_AGENT to
                    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36",
            HttpHeaders.ACCEPT to "*/*",
        )
    }
}